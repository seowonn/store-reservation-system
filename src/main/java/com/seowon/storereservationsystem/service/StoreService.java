package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.StoreRegistrationDto;
import com.seowon.storereservationsystem.entity.Store;

public interface StoreService {
    Store registerStore(StoreRegistrationDto registrationDto);
}
