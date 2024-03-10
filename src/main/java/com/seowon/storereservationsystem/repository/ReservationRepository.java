package com.seowon.storereservationsystem.repository;

import com.seowon.storereservationsystem.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.user.userId = :userId AND r.reserveTime = :now")
    List<Reservation> findByUserUserIdAndReserveTime(String userId, LocalDateTime now);
    @Query("SELECT r FROM Reservation r WHERE r.user.userId = :userId AND r.checkIn = true")
    List<Reservation> findByUserUserIdAndCheckInTrue(String userId);
    @Query("SELECT r FROM Reservation r WHERE r.store.id = :storeId ORDER BY r.reserveTime DESC")
    List<Reservation> findByStoreIdOrderByReserveTimeDesc(Long storeId);
}
