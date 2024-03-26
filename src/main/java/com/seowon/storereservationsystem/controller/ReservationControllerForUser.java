package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.ApiResponse;
import com.seowon.storereservationsystem.dto.ReservationDto;
import com.seowon.storereservationsystem.service.UserReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/reservation")
public class ReservationControllerForUser {

    private final UserReservationService userReservationService;

    /**
     * 사용자의 매장 예약 신청 서비스
     * @PathVariable storeId
     * @RequestBody reservationDto
     * 예약 결과와 예약 번호(reservationId)를 수령하게 된다.
     */
    @PostMapping("/apply/{storeId}")
    public ResponseEntity<ReservationDto> applyReservation(
            @PathVariable Long storeId,
            @RequestBody ReservationDto reservationDto
    ){
        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        reservationDto.setUserId(userId);
        ReservationDto result =
                userReservationService.applyReservation(storeId, reservationDto);
        return ResponseEntity.ok(result);
    }

    /**
     * 사용자 방문 확인 서비스
     * 예약 시간 기준 10분 내외로만 확인 가능
     * @PathVariable reservationId
     */
    @PostMapping("/{reservationId}/check-in")
    public ResponseEntity<?> reserveCheck(@PathVariable Long reservationId){
        ApiResponse apiResponse =
                userReservationService.checkReservation(reservationId);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{reservationId}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable Long reservationId){
        ApiResponse apiResponse =
                userReservationService.cancelReservation(reservationId);
        return ResponseEntity.ok(apiResponse);
    }
}
