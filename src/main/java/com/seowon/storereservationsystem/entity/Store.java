package com.seowon.storereservationsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store extends BaseEntity{
    @Column(nullable = false)
    private String storeName;

    private Integer seatingCapacity;

    @Column(nullable = false)
    private String storePhoneNumber;

    @Column(nullable = false)
    private String storeLocation;

    private String storeDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private Owner owner;
}
