package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.ReviewDto;
import com.seowon.storereservationsystem.entity.Reservation;
import com.seowon.storereservationsystem.entity.Review;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.ReservationRepository;
import com.seowon.storereservationsystem.repository.ReviewRepository;
import com.seowon.storereservationsystem.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.seowon.storereservationsystem.type.ErrorCode.VISITED_UNCHECK;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public List<Reservation> getUsersReviewableStores(String userId) {
        return reservationRepository.findByUserUserIdAndCheckInTrue(userId);
    }

    @Override
    public ReviewDto createReview(Long reservationId, ReviewDto reviewDto) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationSystemException(
                        VISITED_UNCHECK
                ));

        Review review = Review.builder()
                .userId(reservation.getUser().getUserId())
                .userName(reviewDto.getUserName())
                .storeName(reservation.getStore().getStoreName())
                .visitedAt(reservation.getReserveTime())
                .content(reviewDto.getContent())
                .build();
        reviewRepository.save(review);

        reviewDto.setReservedDt(reservation.getReserveTimeTextFormat());

        return reviewDto;
    }

}
