package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.ReservationDto;

public interface ReservationService {
    ReservationDto makeReservation(ReservationDto reservationDto);
}
