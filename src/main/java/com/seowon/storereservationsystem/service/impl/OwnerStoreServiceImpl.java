package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.StoreRegistrationDto;
import com.seowon.storereservationsystem.entity.Owner;
import com.seowon.storereservationsystem.entity.Store;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.OwnerRepository;
import com.seowon.storereservationsystem.repository.StoreRepository;
import com.seowon.storereservationsystem.service.OwnerStoreService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.seowon.storereservationsystem.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class OwnerStoreServiceImpl implements OwnerStoreService {

    private final StoreRepository storeRepository;
    private final OwnerRepository ownerRepository;
    private final Trie<String, String> trie;

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
    public void addAutocompleteKeyword(String keyword){
        trie.put(keyword, null);
    }

    @Override
    public Page<Store> selectOwnersStore(String ownerId, Pageable pageable) {
        Owner owner = ownerRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new ReservationSystemException(UNREGISTERED_USER));

        return storeRepository
                .findAllByOwnerOwnerId(owner.getOwnerId(), pageable);
    }

    @Override
    public Store updateStore(StoreRegistrationDto registrationDto, Long storeId) {
        // Store 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ReservationSystemException(UNREGISTERED_STORE));

        if(!store.getOwner().getOwnerId().equals(registrationDto.getOwnerId())) {
            throw new ReservationSystemException(UNREGISTERED_USER);
        }

        store.setStoreName(registrationDto.getStoreName());
        store.setSeatingCapacity(registrationDto.getSeatingCapacity());
        store.setStorePhoneNumber(registrationDto.getStorePhoneNumber());
        store.setStoreLocation(registrationDto.getStoreLocation());
        store.setStoreDescription(registrationDto.getStoreDescription());

        return storeRepository.save(store);
    }

    @Override
    public void deleteStore(String ownerId, Long storeId) {
        // 점주가 소유한 매장인지 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ReservationSystemException(UNREGISTERED_STORE));

        if(!store.getOwner().getOwnerId().equals(ownerId)) {
            throw new ReservationSystemException(ACCESS_DENIED);
        }
        storeRepository.deleteById(storeId);
    }

}
