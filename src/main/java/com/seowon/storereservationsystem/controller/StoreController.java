package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.OwnerRegistrationDto;
import com.seowon.storereservationsystem.dto.StoreRegistrationDto;
import com.seowon.storereservationsystem.entity.Store;
import com.seowon.storereservationsystem.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner/store")
public class StoreController {
    private final StoreService storeService;

    /**
     * 점주의 매장 생성
     * @param registrationDto
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<?> addStore(
            @RequestBody StoreRegistrationDto registrationDto) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String ownerId = authentication.getName();
        registrationDto.setOwnerId(ownerId);
        Store store = storeService.registerStore(registrationDto);
        return ResponseEntity.ok(store);
    }

    // 점주의 매장 목록 보기 (Read)

    /**
     * 특정 칼럼만 추출하는 쿼리
     *
     * @Query("SELECT p.name, p.price FROM Product p WHERE p.name = :name")
     * List<Object[]> findByNameParam(@Param("name") String name);
     */
    @PostMapping("/list")
    public ResponseEntity<?> showStoreList(
            @RequestBody OwnerRegistrationDto registrationDto) {
        return null;
    }

    // 점주의 매장 정보 갱신 (Update)
    @PutMapping("/update")
    public ResponseEntity<?> updateStore(
            @RequestBody OwnerRegistrationDto registrationDto) {
        return null;
    }

    // 점주의 매장 삭제 (Delete)
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteStore(
            @RequestBody OwnerRegistrationDto registrationDto) {
        return null;
    }
}
