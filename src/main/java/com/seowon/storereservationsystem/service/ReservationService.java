package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.ApiResponse;
import com.seowon.storereservationsystem.dto.ReservationDto;

public interface ReservationService {
    ReservationDto applyReservation(Long storeId, ReservationDto reservationDto);
    ApiResponse checkReservation(Long reservationId);
}
