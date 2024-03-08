package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.ReviewDto;
import com.seowon.storereservationsystem.entity.Reservation;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(Long storeId, ReviewDto reviewDto);
    List<Reservation> getUsersReviewableStores(String userId);
    ReviewDto updateReviewContent(String userId, Long reviewId, String content);
    void deleteReview(Long reviewId);
}
