package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.StoreRegistrationDto;
import com.seowon.storereservationsystem.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreService {
    Store registerStore(StoreRegistrationDto registrationDto);
    List<String> selectOwnersStore(String ownerId);
    Page<Store> getAllStores(Pageable pageable);
    List<Store> getStoresByStoreName(String storeName);
    void addAutocompleteKeyword(String keyword);
    List<String> autocomplete(String prefix);
    void deleteAutocompleteKeyword(String keyword);

    Store getStoreInfo(Long storeId);
}
