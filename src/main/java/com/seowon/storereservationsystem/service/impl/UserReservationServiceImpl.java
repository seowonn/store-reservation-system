package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.ApiResponse;
import com.seowon.storereservationsystem.dto.ReservationDto;
import com.seowon.storereservationsystem.entity.Reservation;
import com.seowon.storereservationsystem.entity.Store;
import com.seowon.storereservationsystem.entity.User;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.exception.UserNoticeException;
import com.seowon.storereservationsystem.repository.ReservationRepository;
import com.seowon.storereservationsystem.repository.StoreRepository;
import com.seowon.storereservationsystem.repository.UserRepository;
import com.seowon.storereservationsystem.service.UserReservationService;
import com.seowon.storereservationsystem.type.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.seowon.storereservationsystem.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserReservationServiceImpl implements UserReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    @Override
    public ReservationDto applyReservation(Long storeId, ReservationDto reservationDto) {

        // 예약 시간이 이미 지난(현재 대비 이전) 시간대인지 확인
        if(convertStringToLocalDateTime(reservationDto.getReserveTime())
                .isBefore(LocalDateTime.now())){
            throw new ReservationSystemException(IMPOSSIBLE_RESERVE_TIME);
        }

        // 동일한 사용자가 같은 날짜에 대하여 예약을 또 했는지 확인
        List<Reservation> reservationList =
                reservationRepository.findByUserUserIdAndReserveTime(
                        reservationDto.getUserId(), LocalDateTime.now());

        // 같은 날짜 예약이여도 3시간 이상 간격이면 문제 X
        if(!reservationList.isEmpty()){
            for (Reservation reservation : reservationList) {
                if(LocalDateTime.now().isBefore(
                        reservation.getReserveTime().plusHours(3))){
                    throw new ReservationSystemException(ALREADY_RESERVED);
                }
            }
        }

        User user = userRepository.findByUserId(reservationDto.getUserId())
                .orElseThrow(() -> new ReservationSystemException(
                        UNREGISTERED_USER
                ));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ReservationSystemException(
                        UNREGISTERED_STORE
                ));

        Reservation reservation = Reservation.builder()
                .reserveTime(convertStringToLocalDateTime(
                        reservationDto.getReserveTime()))
                .reserveNum(reservationDto.getReserveNum())
                .reservationStatus(ReservationStatus.STANDBY.toString())
                .store(store)
                .user(user)
                .build();

        reservationRepository.save(reservation);

        reservationDto.setStoreName(store.getStoreName());
        reservationDto.setReservationId(reservation.getId());
        reservationDto.setReserveResult(ReservationStatus.STANDBY.toString());

        return reservationDto;
    }

    @Override
    public ApiResponse checkReservation(Long reservationId) {
        // 등록된 예약인지 확인
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationSystemException(
                        UNREGISTERED_RESERVATION
                ));

        // 점장의 승인을 받은 예약인지 확인
        if(!reservation.getReservationStatus().equals(ReservationStatus.APPROVED.toString())) {
            throw new UserNoticeException(DENIED_RESERVATION);
        }

        // 예약 10분 전안으로 방문확인을 하러 왔는지 확인
        if(LocalDateTime.now().isAfter(reservation.getReserveTime())){
            throw new UserNoticeException(EXPIRED_RESERVATION);
        }

        if (LocalDateTime.now().isBefore(
                reservation.getReserveTime().minusMinutes(10))){
            return new ApiResponse(
                    false,
                    "예약 시간 10분전부터 방문 확인이 가능합니다.");
        }

        reservation.setCheckIn(true);
        reservationRepository.save(reservation);

        return new ApiResponse(true, "방문 확인되었습니다.");
    }

    @Override
    public ApiResponse cancelReservation(Long reservationId) {

        // 로그인한 사용자의 아이디랑 입력으로 받은 예약 아이디를 비교
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() ->
                        new ReservationSystemException(UNMATCHED_URL_INFO));

        String loginId =
                SecurityContextHolder.getContext().getAuthentication().getName();

        if(!loginId.equals(reservation.getUser().getUserId())) {
            throw new ReservationSystemException(UNREGISTERED_RESERVATION);
        }

        reservationRepository.delete(reservation);

        return ApiResponse.builder()
                .success(true)
                .message("예약을 성공적으로 취소 하였습니다.")
                .build();
    }

    /**
     * "18 : 30" 형식으로 reserveTime 을 받아온다는 가정
     * String timeStr
     * @return LocalDateTime
     */
    public static LocalDateTime convertStringToLocalDateTime(String timeStr) {
        // 문자열에서 공백 제거
        timeStr = timeStr.replaceAll("\\s", "");
        // 문자열을 LocalTime 객체로 파싱
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(timeStr, timeFormatter);

        // 현재 날짜와 파싱된 시간을 결합하여 LocalDateTime 객체 생성
        LocalDate currentDate = LocalDate.now();
        return LocalDateTime.of(currentDate, time);
    }


}
