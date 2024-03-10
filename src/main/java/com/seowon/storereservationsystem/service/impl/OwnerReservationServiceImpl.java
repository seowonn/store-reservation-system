package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.entity.Reservation;
import com.seowon.storereservationsystem.entity.Store;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.OwnerRepository;
import com.seowon.storereservationsystem.repository.ReservationRepository;
import com.seowon.storereservationsystem.repository.StoreRepository;
import com.seowon.storereservationsystem.service.OwnerReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.seowon.storereservationsystem.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class OwnerReservationServiceImpl implements OwnerReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final OwnerRepository ownerRepository;

    @Override
    public List<Reservation> getReservations(String ownerId, Long storeId) {

        // 등록된 매장 점주인지 확인, 해당 매장의 소유자인지 확인
        ownerRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new ReservationSystemException(UNREGISTERED_USER));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ReservationSystemException(UNREGISTERED_STORE));
        if(!Objects.equals(store.getOwner().getOwnerId(), ownerId)) {
            throw new ReservationSystemException(ACCESS_DENIED);
        }

        // 점주임이 확인되었으면 해당 매장에 등록된 예약 내역들을 보여줌.
        return reservationRepository.findByStoreIdOrderByReserveTimeDesc(storeId);
    }
}
