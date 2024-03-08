package com.seowon.storereservationsystem.service;

import com.seowon.storereservationsystem.dto.ReviewDto;
import com.seowon.storereservationsystem.entity.Reservation;

import java.util.List;

public interface ReviewService {
    List<Reservation> getUsersReviewableStores(String userId);
    ReviewDto createReview(Long storeId, ReviewDto reviewDto);
}
