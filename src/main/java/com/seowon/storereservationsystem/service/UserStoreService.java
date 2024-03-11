package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.StoreRegistrationDto;
import com.seowon.storereservationsystem.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserStoreService {
    Page<Store> getAllStores(Pageable pageable);
    Page<Store> getStoresByStoreName(String storeName, Pageable pageable);
    List<String> autocomplete(String prefix);
    void deleteAutocompleteKeyword(String keyword);
    Store getStoreInfo(Long storeId);
}
