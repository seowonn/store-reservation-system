package com.seowon.storereservationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerRegistrationDto {
    private String ownerName;
    private String phone;
    private String ownerId;
    private String password;
}
