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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
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

        // 점주의 로그인 정보에서 아이디 추출
        String checkedLoginId = checkLoginId(registrationDto.getOwnerId());

        // Owner 조회
        Owner owner = ownerRepository.findByOwnerId(checkedLoginId)
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
    public Page<Store> selectOwnersStore(String urlOwnerId, Pageable pageable) {

        // 점주의 로그인 정보에서 아이디 추출
        String loginOwnerId = checkLoginId(urlOwnerId);

        // 점주 회원 존재 유무 판단
        ownerRepository.findByOwnerId(loginOwnerId)
                .orElseThrow(() -> new ReservationSystemException(UNREGISTERED_USER));

        return storeRepository
                .findAllByOwnerOwnerId(loginOwnerId, pageable);
    }

    @Override
    public Store updateStore(StoreRegistrationDto registrationDto, Long storeId) {

        // 점주의 로그인 정보에서 아이디 추출
        String checkedLoginId = checkLoginId(registrationDto.getOwnerId());

        // Store 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ReservationSystemException(UNREGISTERED_STORE));

        // 조회된 매장의 점주 아이디와 로그인한 아이디 정보가 일치하는지 확인한다.
        if(!store.getOwner().getOwnerId().equals(checkedLoginId)) {
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

        String checkedLoginId = checkLoginId(ownerId);

        // 점주가 소유한 매장인지 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ReservationSystemException(UNREGISTERED_STORE));

        if(!store.getOwner().getOwnerId().equals(checkedLoginId)) {
            throw new ReservationSystemException(ACCESS_DENIED);
        }
        storeRepository.deleteById(storeId);
    }

    private static String checkLoginId(String ownerId) {

        // 점주의 로그인 정보에서 아이디 추출
        String checkedLoginId =
                SecurityContextHolder.getContext().getAuthentication().getName();

        if(!checkedLoginId.equals(ownerId)){
            throw new ReservationSystemException(UNMATCHED_URL_INFO);
        }

        return checkedLoginId;
    }

}
