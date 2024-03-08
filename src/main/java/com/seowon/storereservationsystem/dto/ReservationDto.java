package com.seowon.storereservationsystem.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDto {
    private Long reservationId;
    private String userId;
    private String userName;
    private String phone;
    private String storeName;
    private String reserveTime;
    private int reserveNum;
    private ApiResponse reserveResult;
}
