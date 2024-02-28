package com.seowon.storereservationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class UserRegistrationDto {
    private String name;
    private String phone;
    private String userId;
    private String password;

}
