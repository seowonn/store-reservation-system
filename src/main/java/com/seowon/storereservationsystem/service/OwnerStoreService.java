package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.StoreRegistrationDto;
import com.seowon.storereservationsystem.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OwnerStoreService {
    Store registerStore(StoreRegistrationDto registrationDto);
    void addAutocompleteKeyword(String keyword);
    Page<Store> selectOwnersStore(String ownerId, Pageable pageable);
    Store updateStore(StoreRegistrationDto registrationDto, Long storeId);
    void deleteStore(String ownerId, Long storeId);
}
