package com.seowon.storereservationsystem.repository;

import com.seowon.storereservationsystem.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.user.userId = :userId AND r.checkIn = true")
    List<Reservation> findByUserUserIdAndCheckInTrue(String userId);
}
