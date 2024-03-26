package com.seowon.storereservationsystem.repository;

import com.seowon.storereservationsystem.entity.Review;
import com.seowon.storereservationsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Long countByUser(User user);
}
