package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.ReviewDto;
import com.seowon.storereservationsystem.entity.Reservation;
import com.seowon.storereservationsystem.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 사용자가 리뷰를 작성할 수 있는 매장 모음
     * @PathVariable userId
     */
    @GetMapping("/user/{userId}/reviewable-stores")
    public ResponseEntity<?> reviewableStores(@PathVariable String userId) {
        List<Reservation> usersReviewableStores =
                reviewService.getUsersReviewableStores(userId);
        return ResponseEntity.ok(usersReviewableStores);
    }

    /**
     * 위쪽 목록에서 선택한 예약 내역에 대한 리뷰를 작성할 수 있는 기능
     * @PathVariable reservationId
     * @RequestBody reviewDto
     */
    @PostMapping("/user/reviews/{reservationId}")
    public ResponseEntity<?> createReview(
            @PathVariable Long reservationId,
            @RequestBody ReviewDto reviewDto) {
        ReviewDto review = reviewService.createReview(reservationId, reviewDto);
        return ResponseEntity.ok(review);
    }

}
