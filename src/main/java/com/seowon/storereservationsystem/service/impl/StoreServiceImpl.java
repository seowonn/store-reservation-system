package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.StoreRegistrationDto;
import com.seowon.storereservationsystem.entity.Owner;
import com.seowon.storereservationsystem.entity.Store;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.OwnerRepository;
import com.seowon.storereservationsystem.repository.StoreRepository;
import com.seowon.storereservationsystem.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.seowon.storereservationsystem.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

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
    public List<String> selectOwnersStore(String ownerId) {
        Owner owner = ownerRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new ReservationSystemException(UNREGISTERED_USER));
        return storeRepository
                .findStoreNamesByOwnerId(owner.getId());
    }

    @Override
    public Page<Store> getAllStores(Pageable pageable) {
        return storeRepository.findAll(pageable);
    }

    @Override
    public Page<Store> getStoresByStoreName(String storeName, Pageable pageable) {
        Page<Store> storePage =
                storeRepository.findByStoreNameContaining(storeName, pageable);
        if(storePage.isEmpty()) {
            throw new ReservationSystemException(UNREGISTERED_STORE);
        }
        return storePage;
    }

    @Override
    public void addAutocompleteKeyword(String keyword){
        trie.put(keyword, null);
    }

    @Override
    public List<String> autocomplete(String prefix) {
        return trie.prefixMap(prefix).keySet()
                .stream()
                .limit(10)
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAutocompleteKeyword(String keyword) {
        trie.remove(keyword);
    }

    @Override
    public Store getStoreInfo(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() ->
                        new ReservationSystemException(
                                UNREGISTERED_STORE
                        ));
    }

}
