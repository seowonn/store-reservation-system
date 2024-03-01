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
    private Integer seatingCapacity;
    private String storePhoneNumber;
    private String storeLocation;
    private String storeDescription;
}
