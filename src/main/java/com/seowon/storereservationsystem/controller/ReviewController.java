package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.ApiResponse;
import com.seowon.storereservationsystem.dto.ReviewDto;
import com.seowon.storereservationsystem.entity.Reservation;
import com.seowon.storereservationsystem.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 사용자가 리뷰를 작성할 수 있는 매장 모음
     * @PathVariable userId
     */
    @GetMapping("/user/reviews/{userId}/reviewable-stores")
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

    /**
     * 리뷰 내용(content) 수정
     * @PathVariable userId, reviewId
     * @RequestBody updateContent
     */
    @PatchMapping("/user/reviews/{userId}/{reviewId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> updateReviewContent(
            @PathVariable String userId,
            @PathVariable Long reviewId,
            @RequestBody Map<String, String> updateContent) {
        String content = updateContent.get("content");
        ReviewDto reviewDto =
                reviewService.updateReviewContent(userId, reviewId, content);
        return ResponseEntity.ok(reviewDto);
    }

    /**
     * 리뷰 삭제 기능
     * @PathVariable reviewId
     */
    @DeleteMapping("/review/delete/{reviewId}")
    @PreAuthorize("hasAnyAuthority('USER', 'OWNER')")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        ApiResponse apiResponse = ApiResponse.builder()
                .success(true)
                .message("리뷰를 성공적으로 삭제하였습니다.")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
