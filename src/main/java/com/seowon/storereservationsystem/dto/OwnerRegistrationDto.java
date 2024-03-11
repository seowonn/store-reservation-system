package com.seowon.storereservationsystem.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerRegistrationDto {
    private String ownerName;
    private String phone;
    private String ownerId;
    private String password;
}
