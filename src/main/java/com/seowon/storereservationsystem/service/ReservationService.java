package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.ApiResponse;
import com.seowon.storereservationsystem.dto.ReservationDto;

public interface ReservationService {
    ReservationDto makeReservation(Long storeId, ReservationDto reservationDto);
    ApiResponse checkReservation(Long reservationId);
}
