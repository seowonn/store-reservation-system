package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.ApiResponse;
import com.seowon.storereservationsystem.dto.OwnerRegistrationDto;
import com.seowon.storereservationsystem.dto.StoreRegistrationDto;
import com.seowon.storereservationsystem.entity.Store;
import com.seowon.storereservationsystem.service.OwnerStoreService;
import com.seowon.storereservationsystem.service.UserStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner/store")
public class StoreControllerForOwner {
    private final OwnerStoreService ownerStoreService;

    /**
     * 점주의 매장 생성
     * @RequestBody registrationDto
     */
    @PostMapping("/add/{ownerId}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> addStore(
            @RequestBody StoreRegistrationDto registrationDto,
            @PathVariable String ownerId) {
        registrationDto.setOwnerId(ownerId);
        Store store = ownerStoreService.registerStore(registrationDto);

        ownerStoreService.addAutocompleteKeyword(store.getStoreName());

        return ResponseEntity.ok(store);
    }

    /**
     * 점주가 등록한 매장 조회
     */
    @GetMapping("/store-list/{ownerId}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> getStoreList(
            @PathVariable String ownerId, Pageable pageable) {
        Page<Store> store = ownerStoreService.selectOwnersStore(ownerId, pageable);
        return ResponseEntity.ok(store);
    }

    /**
     * 점주가 등록한 매장 수정
     * @RequestBody registrationDto
     */
    @PutMapping("/update/{ownerId}/{storeId}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> updateStore(
            @RequestBody StoreRegistrationDto registrationDto,
            @PathVariable String ownerId,
            @PathVariable Long storeId) {
        registrationDto.setOwnerId(ownerId);
        Store store = ownerStoreService.updateStore(registrationDto, storeId);
        return ResponseEntity.ok(store);
    }

    /**
     * 점주가 등록한 매장 삭제
     * @PathVariable ownerId
     * @PathVariable storeId
     */
    @DeleteMapping("/delete/{ownerId}/{storeId}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> deleteStore(
            @PathVariable String ownerId,
            @PathVariable Long storeId) {
        ownerStoreService.deleteStore(ownerId, storeId);
        ApiResponse apiResponse = ApiResponse.builder()
                .success(true)
                .message("매장을 성공적으로 삭제하였습니다.")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
