package com.seowon.storereservationsystem.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
    STANDBY("대기"),
    APPROVED("승인"),
    REJECTED("거절");

    private final String status;
}
