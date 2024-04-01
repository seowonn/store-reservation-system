package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.ApiResponse;
import com.seowon.storereservationsystem.entity.Reservation;
import com.seowon.storereservationsystem.type.ReservationStatus;

import java.util.List;

public interface OwnerReservationService {
    List<Reservation> getStandByReservations(String ownerId, Long storeId);
    ApiResponse setReservationStatus(Long reservationId, String status);
}
