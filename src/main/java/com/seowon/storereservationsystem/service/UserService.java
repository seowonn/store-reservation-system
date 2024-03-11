package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.LoginRequest;
import com.seowon.storereservationsystem.dto.UserRegistrationDto;
import com.seowon.storereservationsystem.entity.User;

public interface UserService {
    User getUserProfile(String userId);
    void updateUser(UserRegistrationDto registrationDto, String id);
    void deleteUser(LoginRequest loginRequest);

    void resetUserPassword(Long id);
    void sendSMS(String message);
}
