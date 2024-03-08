package com.seowon.storereservationsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Review extends BaseEntity {
    @Column
    private String userId;

    @Column
    private String userName;

    @Column
    private String storeName;

    @Column
    private LocalDateTime visitedAt;

    @Column(length = 10000)
    private String content;
}
