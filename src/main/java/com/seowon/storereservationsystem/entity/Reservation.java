package com.seowon.storereservationsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation extends BaseEntity{
    @Column(nullable = false)
    private LocalDateTime reserveTime;

    @Column
    private int reserveNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store")
    @JsonBackReference
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    @JsonBackReference
    private User user;
}
