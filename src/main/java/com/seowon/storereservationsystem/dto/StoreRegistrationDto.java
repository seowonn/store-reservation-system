package com.seowon.storereservationsystem.dto;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
public class StoreRegistrationDto {
    private String ownerId;
    private String storeName;
    private String phone;
    private Integer seatingCapacity;
}
