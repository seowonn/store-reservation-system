package com.seowon.storereservationsystem.repository;

import com.seowon.storereservationsystem.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, String> {
    Optional<Owner> findByOwnerIdAndStoreName(String id, String storeName);
}
