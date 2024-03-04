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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner/store")
public class StoreControllerForOwner {
    private final StoreService storeService;

    /**
     * 점주의 매장 생성
     *
     * @RequestBody registrationDto
     */
    @PostMapping("/add")
    public ResponseEntity<?> addStore(
            @RequestBody StoreRegistrationDto registrationDto) {
        String ownerId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        registrationDto.setOwnerId(ownerId);
        Store store = storeService.registerStore(registrationDto);

        storeService.addAutocompleteKeyword(store.getStoreName());

        return ResponseEntity.ok(store);
    }

    /**
     * 점주가 등록한 매장 조회
     */
    @GetMapping("/store-list")
    public ResponseEntity<List<String>> getStoreList() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        List<String> store = storeService.selectOwnersStore(authentication.getName());
        return ResponseEntity.ok(store);
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
