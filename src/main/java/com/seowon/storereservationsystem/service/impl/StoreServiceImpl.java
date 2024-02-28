package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.StoreRegistrationDto;
import com.seowon.storereservationsystem.entity.Owner;
import com.seowon.storereservationsystem.entity.Store;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.OwnerRepository;
import com.seowon.storereservationsystem.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.seowon.storereservationsystem.type.ErrorCode.ALREADY_REGISTERED_STORE;
import static com.seowon.storereservationsystem.type.ErrorCode.UNREGISTERED_USER;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final OwnerRepository ownerRepository;
    @Override
    public Store registerStore(StoreRegistrationDto registrationDto) {
        Optional<Owner> optionalOwner =
                ownerRepository.findById(registrationDto.getOwnerId());
        if(optionalOwner.isEmpty()) {
            throw new ReservationSystemException(UNREGISTERED_USER);
        }

        Optional<Owner> optional = ownerRepository.findByOwnerIdAndStoreName(
                registrationDto.getOwnerId(), registrationDto.getStoreName());
        if(optional.isEmpty()) {
            throw new ReservationSystemException(ALREADY_REGISTERED_STORE);
        }

        return Store.builder()
                .storeName(registrationDto.getStoreName())
                .phone(registrationDto.getPhone())
                .ownerId(registrationDto.getOwnerId())
                .seatingCapacity(registrationDto.getSeatingCapacity())
                .regAt(LocalDateTime.now())
                .build();
    }
}
