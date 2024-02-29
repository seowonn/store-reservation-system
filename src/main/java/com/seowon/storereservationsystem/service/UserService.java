package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.UserRegistrationDto;
import com.seowon.storereservationsystem.entity.User;

public interface UserService {
    User register(UserRegistrationDto registrationDto);
    User getUserInfo(String userId);
}
