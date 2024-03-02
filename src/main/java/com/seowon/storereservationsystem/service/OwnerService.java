package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.LoginInput;
import com.seowon.storereservationsystem.dto.OwnerRegistrationDto;
import com.seowon.storereservationsystem.entity.Owner;

import java.util.List;

public interface OwnerService {
    Owner register(OwnerRegistrationDto registrationDto);
    Owner selectOwnerProfile(String ownerId);
    Owner updateOwner();
    void deleteOwner(LoginInput loginInput);
}
