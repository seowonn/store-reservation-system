package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.entity.Reservation;

import java.util.List;

public interface OwnerReservationService {
    List<Reservation> getReservations(String ownerId, Long storeId);
}
