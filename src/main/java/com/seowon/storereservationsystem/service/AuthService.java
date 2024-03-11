package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.LoginRequest;
import com.seowon.storereservationsystem.dto.LoginResponse;
import com.seowon.storereservationsystem.dto.OwnerRegistrationDto;
import com.seowon.storereservationsystem.dto.UserRegistrationDto;
import com.seowon.storereservationsystem.entity.Owner;
import com.seowon.storereservationsystem.entity.User;

public interface AuthService {
    LoginResponse authenticateUser(LoginRequest loginRequest);
    LoginResponse authenticateOwner(LoginRequest loginRequest);

    User registerUser(UserRegistrationDto registrationDto);

    Owner registerOwner(OwnerRegistrationDto registrationDto);
}
