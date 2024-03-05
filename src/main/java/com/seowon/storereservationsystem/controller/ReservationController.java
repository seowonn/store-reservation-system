package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.ReservationDto;
import com.seowon.storereservationsystem.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/{storeId}")
    public ResponseEntity<?> reserve(
            @PathVariable Long storeId,
            @RequestBody ReservationDto reservationDto
    ){
        reservationDto.setStoreId(storeId);
        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        reservationDto.setUserId(userId);
        ReservationDto result =
                reservationService.makeReservation(reservationDto);
        return ResponseEntity.ok(result);
    }

}
