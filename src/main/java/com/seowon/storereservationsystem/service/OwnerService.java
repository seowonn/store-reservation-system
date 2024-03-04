package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.LoginInput;
import com.seowon.storereservationsystem.dto.OwnerRegistrationDto;
import com.seowon.storereservationsystem.entity.Owner;

public interface OwnerService {
    Owner register(OwnerRegistrationDto registrationDto);
    Owner getOwnerProfile(String ownerId);
    void updateOwner(OwnerRegistrationDto registrationDto, String ownerId);
    void deleteOwner(LoginInput loginInput);
}
