package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.ReservationDto;
import com.seowon.storereservationsystem.entity.Reservation;
import com.seowon.storereservationsystem.entity.Store;
import com.seowon.storereservationsystem.entity.User;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.ReservationRepository;
import com.seowon.storereservationsystem.repository.StoreRepository;
import com.seowon.storereservationsystem.repository.UserRepository;
import com.seowon.storereservationsystem.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.seowon.storereservationsystem.type.ErrorCode.UNREGISTERED_STORE;
import static com.seowon.storereservationsystem.type.ErrorCode.UNREGISTERED_USER;
import static com.seowon.storereservationsystem.type.Result.SUCCESS;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    @Override
    public ReservationDto makeReservation(ReservationDto reservationDto) {

        Store store = storeRepository.findById(reservationDto.getStoreId())
                .orElseThrow(() -> new ReservationSystemException(
                        UNREGISTERED_STORE
                ));
        User user = userRepository.findByUserId(reservationDto.getUserId())
                .orElseThrow(() -> new ReservationSystemException(
                        UNREGISTERED_USER
                ));

        Reservation reservation = Reservation.builder()
                .reserveTime(convertStringToLocalDateTime(
                        reservationDto.getReserveTime()))
                .reserveNum(reservationDto.getReserveNum())
                .store(store)
                .user(user)
                .build();

        reservationRepository.save(reservation);

        reservationDto.setStoreName(store.getStoreName());
        reservationDto.setReserveResult(SUCCESS.getResult());

        return reservationDto;
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
