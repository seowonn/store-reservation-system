package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.entity.Store;
import com.seowon.storereservationsystem.service.UserStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/store")
public class StoreControllerForUser {

    private final UserStoreService userStoreService;

    /**
     * 매장명 검색을 통한 조회 기능
     * 검색 단어를 포함하는 모든 결과를 반환,
     * 검색 단어가 없으면 전체 매장 결과를 반환
     * 추가해볼 것 : 사용자 현 위치 + 매장 위치 계산 가까운 매장 n개 조회
     * @RequestParam keyword
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchStoreInfo(
            @RequestParam(required = false) String keyword,
            Pageable pageable){
        Page<Store> stores;
        if(keyword != null && !keyword.isEmpty()) {
            stores = userStoreService.getStoresByStoreName(keyword, pageable);
        } else {
            stores = userStoreService.getAllStores(pageable);
        }

        return ResponseEntity.ok(stores);
    }

    /**
     * 위의 검색 결과에서 나온
     * 매장 목록들 중
     * 선택한 매장의 상세정보 조회
     * @RequestBody storeId
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<?> storeInfo(@PathVariable Long storeId){
        Store store = userStoreService.getStoreInfo(storeId);
        return ResponseEntity.ok(store);
    }

    /**
     * 자동 완성을 통한 매장 검색 기능
     */
    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(@RequestParam String keyword) {
        List<String> autocomplete = userStoreService.autocomplete(keyword);
        return ResponseEntity.ok(autocomplete);
    }
}
