package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.entity.Store;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.StoreRepository;
import com.seowon.storereservationsystem.service.UserStoreService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.seowon.storereservationsystem.type.ErrorCode.UNREGISTERED_STORE;

@Service
@RequiredArgsConstructor
public class UserStoreServiceImpl implements UserStoreService {

    private final StoreRepository storeRepository;
    private final Trie<String, String> trie;

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
