package com.seowon.storereservationsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(columnDefinition = "LONGTEXT")
    private String storeDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    @JsonBackReference
    private Owner owner;

    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonManagedReference
    private List<Review> reviewList;
}
