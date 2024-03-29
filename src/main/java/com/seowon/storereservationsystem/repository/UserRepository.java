package com.seowon.storereservationsystem.repository;

import com.seowon.storereservationsystem.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    @Transactional
    void deleteByUserId(String ownerId);
}
