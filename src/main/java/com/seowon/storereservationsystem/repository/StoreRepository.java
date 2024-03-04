package com.seowon.storereservationsystem.repository;

import com.seowon.storereservationsystem.entity.Owner;
import com.seowon.storereservationsystem.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByOwnerAndStoreName(Owner owner, String storeName);
    @Query("SELECT s.storeName FROM Store s WHERE s.owner.id = :ownerId")
    List<String> findStoreNamesByOwnerId(Long ownerId);
    Page<Store> findAll(Pageable pageable);
    List<Store> findByStoreNameContaining(String storeName);
}
