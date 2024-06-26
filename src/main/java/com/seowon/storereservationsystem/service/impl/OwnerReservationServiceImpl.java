package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.ApiResponse;
import com.seowon.storereservationsystem.dto.EmailMessage;
import com.seowon.storereservationsystem.entity.Reservation;
import com.seowon.storereservationsystem.entity.Store;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.OwnerRepository;
import com.seowon.storereservationsystem.repository.ReservationRepository;
import com.seowon.storereservationsystem.repository.StoreRepository;
import com.seowon.storereservationsystem.service.EmailService;
import com.seowon.storereservationsystem.service.OwnerReservationService;
import com.seowon.storereservationsystem.type.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.seowon.storereservationsystem.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class OwnerReservationServiceImpl implements OwnerReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final OwnerRepository ownerRepository;
    private final EmailService emailService;

    @Override
    public List<Reservation> getStandByReservations(String ownerId, Long storeId) {

        List<Reservation> standByReservations = new ArrayList<>();

        // 등록된 매장 점주인지 확인, 해당 매장의 소유자인지 확인
        ownerRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new ReservationSystemException(UNREGISTERED_USER));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ReservationSystemException(UNREGISTERED_STORE));
        if(!Objects.equals(store.getOwner().getOwnerId(), ownerId)) {
            throw new ReservationSystemException(ACCESS_DENIED);
        }

        // 점주임이 확인되었으면 해당 매장에 등록된 대기 중인 예약 내역들을 보여줌.
        List<Reservation> list =
                reservationRepository.findByStoreIdOrderByReserveTimeDesc(storeId);
        for (Reservation reservation : list) {
            if(reservation.getReservationStatus().equals(ReservationStatus.STANDBY.toString())){
                standByReservations.add(reservation);
            }
        }
        return standByReservations;
    }

    /**
     * 점장은 대기 상태인 예약들을 승인 또는 거절로 상태 변경할 수 있다.
     * 승인, 거절에 대한 알림을 사용자에게 이메일로 전달
     */
    @Override
    public ApiResponse setReservationStatus(Long reservationId, String status) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationSystemException(UNREGISTERED_RESERVATION));

        // 예약을 한, "대기" 상태의 예약인지 확인해야 한다.
        if(!reservation.getReservationStatus().equals(ReservationStatus.STANDBY.toString())){
            throw new ReservationSystemException(UNREGISTERED_RESERVATION);
        }

        // 예약이 이미 "승인/거절" 상태라면 예외로 처리한다.
        if(reservation.getReservationStatus().equals(ReservationStatus.APPROVED.toString())
                || reservation.getReservationStatus().equals(ReservationStatus.REJECTED.toString())){
            throw new ReservationSystemException(ALREADY_PROCESSED);
        }

        reservation.setReservationStatus(status);

        EmailMessage emailMessage = EmailMessage.builder()
                .to(reservation.getUser().getUserId())
                .message("매장 예약 : " + status)
                .subject(reservation.getStore().getId().toString())
                .build();

        ApiResponse apiResponse = emailService.sendReservationStatusMail(emailMessage);

        reservationRepository.save(reservation);

        return apiResponse;
    }
}
