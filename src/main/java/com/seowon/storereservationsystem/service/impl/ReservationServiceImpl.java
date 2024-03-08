package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.ApiResponse;
import com.seowon.storereservationsystem.dto.ReservationDto;
import com.seowon.storereservationsystem.entity.Reservation;
import com.seowon.storereservationsystem.entity.Store;
import com.seowon.storereservationsystem.entity.User;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.ReservationRepository;
import com.seowon.storereservationsystem.repository.StoreRepository;
import com.seowon.storereservationsystem.repository.UserRepository;
import com.seowon.storereservationsystem.service.ReservationService;
import com.seowon.storereservationsystem.type.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.seowon.storereservationsystem.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    @Override
    public ReservationDto applyReservation(Long storeId, ReservationDto reservationDto) {

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
                .reservationStatus(ReservationStatus.STANDBY.getStatus())
                .store(store)
                .user(user)
                .build();

        reservationRepository.save(reservation);

        reservationDto.setStoreName(store.getStoreName());
        reservationDto.setReservationId(reservation.getId());
        reservationDto.setReserveResult(ReservationStatus.STANDBY.getStatus());

        return reservationDto;
    }

    @Override
    public ApiResponse checkReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationSystemException(
                        UNREGISTERED_RESERVATION
                ));

        if(LocalDateTime.now().isAfter(
                reservation.getReserveTime().plusMinutes(10))){
            throw new ReservationSystemException(EXPIRED_RESERVATION);
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
