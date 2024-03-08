package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.LoginInput;
import com.seowon.storereservationsystem.dto.UserRegistrationDto;
import com.seowon.storereservationsystem.entity.User;

public interface UserService {
    User register(UserRegistrationDto registrationDto);
    User getUserProfile(String userId);
    void updateUser(UserRegistrationDto registrationDto, String id);
    void deleteUser(LoginInput loginInput);

    void resetUserPassword(Long id);
    void sendSMS(String message);
}
