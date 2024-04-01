package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.ApiResponse;
import com.seowon.storereservationsystem.dto.ReservationStatusDto;
import com.seowon.storereservationsystem.entity.Reservation;
import com.seowon.storereservationsystem.service.OwnerReservationService;
import com.seowon.storereservationsystem.type.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner/reservation")
public class ReservationControllerForOwner {

    private final OwnerReservationService ownerReservationService;

    /**
     * 매장 점주가 매장에 등록된 예약 목록을 확인
     * @PathVariable ownerId
     * @PathVariable storeId
     */
    @GetMapping("/standby/{ownerId}/{storeId}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> getReservations(
            @PathVariable String ownerId, @PathVariable Long storeId){
        List<Reservation> reservations =
                ownerReservationService.getStandByReservations(ownerId, storeId);
        return ResponseEntity.ok(reservations);
    }

    /**
     * 위의 목록에서 특정 예약으로 들어가서 점주가 예약 상태를 변경할 수 있음
     * @PathVariable reservationId
     * @RequestBody status
     */
    @PatchMapping("/{reservationId}/reserve-status")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> setReservationStatus(
            @PathVariable Long reservationId,
            @RequestBody ReservationStatusDto statusDto) {
        String status = statusDto.getStatus();
        ApiResponse apiResponse =
                ownerReservationService.setReservationStatus(reservationId, status);
        return ResponseEntity.ok(apiResponse);
    }

}
