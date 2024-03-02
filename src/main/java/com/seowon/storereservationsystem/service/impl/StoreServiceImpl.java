package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.StoreRegistrationDto;
import com.seowon.storereservationsystem.entity.Owner;
import com.seowon.storereservationsystem.entity.Store;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.OwnerRepository;
import com.seowon.storereservationsystem.repository.StoreRepository;
import com.seowon.storereservationsystem.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.seowon.storereservationsystem.type.ErrorCode.ALREADY_REGISTERED_STORE;
import static com.seowon.storereservationsystem.type.ErrorCode.UNREGISTERED_USER;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final OwnerRepository ownerRepository;
    @Override
    public Store registerStore(StoreRegistrationDto registrationDto) {
        // Owner 조회
        Owner owner = ownerRepository.findByOwnerId(registrationDto.getOwnerId())
                .orElseThrow(() ->
                        new ReservationSystemException(UNREGISTERED_USER)
                );

        // 동일한 Owner ID와 매장 이름으로 등록된 매장이 있는지 확인
        Optional<Store> optionalStore = storeRepository.findByOwnerAndStoreName(
                owner, registrationDto.getStoreName());
        if(optionalStore.isPresent()){
            throw new ReservationSystemException(ALREADY_REGISTERED_STORE);
        }

        // 매장 등록
        Store store = Store.builder()
                .storeName(registrationDto.getStoreName())
                .storePhoneNumber(registrationDto.getStorePhoneNumber())
                .storeLocation(registrationDto.getStoreLocation())
                .storeDescription(registrationDto.getStoreDescription())
                .seatingCapacity(registrationDto.getSeatingCapacity())
                .owner(owner)
                .build();

        return storeRepository.save(store);
    }

    @Override
    public List<String> selectStores(String ownerId) {
        Owner owner = ownerRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new ReservationSystemException(UNREGISTERED_USER));
        return storeRepository
                .findStoreNamesByOwnerId(owner.getId());
    }
}
