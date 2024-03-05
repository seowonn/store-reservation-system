package com.seowon.storereservationsystem.repository;

import com.seowon.storereservationsystem.entity.Owner;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByOwnerId(String ownerId);
    @Transactional
    void deleteByOwnerId(String ownerId);
}
