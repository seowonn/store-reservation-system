package com.seowon.storereservationsystem.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
    STANDBY,
    APPROVED,
    REJECTED;
}
