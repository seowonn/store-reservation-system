package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.StoreRegistrationDto;
import com.seowon.storereservationsystem.entity.Store;

import java.util.List;

public interface StoreService {
    Store registerStore(StoreRegistrationDto registrationDto);
    List<String> selectStores(String ownerId);
}
