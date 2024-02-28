package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.LoginInput;
import com.seowon.storereservationsystem.dto.UserRegistrationDto;
import com.seowon.storereservationsystem.entity.User;

public interface UserService {
    User register(UserRegistrationDto registrationDto);
    User login(LoginInput loginInput);
    User getUserInfo(String userId);
}
