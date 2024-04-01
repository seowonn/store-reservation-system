package com.seowon.storereservationsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store")
    @JsonBackReference
    private Store store;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = true,
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    @JsonBackReference
    private User user;

    public String getVisitedAtTextFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        return visitedAt.format(formatter);
    }
}
