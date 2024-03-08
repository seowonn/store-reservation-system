package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.ReviewDto;
import com.seowon.storereservationsystem.entity.Reservation;
import com.seowon.storereservationsystem.entity.Review;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.ReservationRepository;
import com.seowon.storereservationsystem.repository.ReviewRepository;
import com.seowon.storereservationsystem.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.seowon.storereservationsystem.type.ErrorCode.*;

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
        Review saved = reviewRepository.save(review);

        reviewDto.setReservedDt(reservation.getReserveTimeTextFormat());
        reviewDto.setReviewId(saved.getId());

        return reviewDto;
    }

    @Override
    public ReviewDto updateReviewContent(String userId, Long reviewId, String content) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReservationSystemException(NO_REVIEW));

        if(!Objects.equals(review.getUserId(), userId)) {
            throw new ReservationSystemException(UNAUTHORIZED_REVIEWER);
        }
        review.setContent(content);

        Review updatedReview = reviewRepository.save(review);

        return ReviewDto.builder()
                .reviewId(updatedReview.getId())
                .userName(updatedReview.getUserName())
                .storeName(updatedReview.getStoreName())
                .reservedDt(updatedReview.getVisitedAtTextFormat())
                .content(updatedReview.getContent())
                .build();
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReservationSystemException(NO_REVIEW));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities =
                authentication.getAuthorities();

        boolean isUser = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
        boolean isOwner = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"));

        if(isUser){
            if(!Objects.equals(review.getUserId(), username)){
                throw new ReservationSystemException(UNAUTHORIZED_REVIEWER);
            }
        } else if (isOwner) {
            if(!Objects.equals(review.getStore().getOwner().getOwnerId(), username)){
                throw new ReservationSystemException(UNAUTHORIZED_REVIEWER);
            }
        }

        reviewRepository.delete(review);
    }
}
