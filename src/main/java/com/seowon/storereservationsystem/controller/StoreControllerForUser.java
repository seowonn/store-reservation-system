package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.entity.Store;
import com.seowon.storereservationsystem.service.StoreService;
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
    private final StoreService storeService;
    /**
     * 사용자가 등록된 매장들을 조회할 수 있는 기능
     * 추가해볼 것 : 사용자 현 위치 + 매장 위치 계산 가까운 매장 n개 조회
     * 등록된 모든 매장 조회 기능
     * 대용량 데이터에 더 적합한 Pageable 이용
     * @Pageable size
     */
    @GetMapping("/search-whole")
    public ResponseEntity<?> searchStores(final Pageable pageable) {
        Page<Store> allStores = storeService.getAllStores(pageable);
        return ResponseEntity.ok(allStores);
    }

    /**
     * 매장명 검색을 통한 조회 기능
     * 검색 단어를 포함하는 모든 결과를 반환
     * @PathVariable storeName
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchStoreInfo(@PathVariable String keyword){
        List<Store> stores = storeService.getStoresByStoreName(keyword);
        return ResponseEntity.ok(stores);
    }

    /**
     * 위의 검색 결과에서 나온
     * 매장 목록들 중
     * 선택한 매장의 상세정보 조회
     * @RequestBody storeId
     */
    @GetMapping("/search")
    public ResponseEntity<?> storeInfo(@RequestParam Long storeId){
        Store store = storeService.getStoreInfo(storeId);
        return ResponseEntity.ok(store);
    }

    /**
     * 자동 완성을 통한 매장 검색 기능
     */
    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(@RequestParam String keyword) {
        List<String> autocomplete = storeService.autocomplete(keyword);
        return ResponseEntity.ok(autocomplete);
    }
}
