package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.LoginInput;
import com.seowon.storereservationsystem.dto.OwnerRegistrationDto;
import com.seowon.storereservationsystem.dto.StoreRegistrationDto;
import com.seowon.storereservationsystem.entity.Owner;
import com.seowon.storereservationsystem.entity.Store;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.service.OwnerService;
import com.seowon.storereservationsystem.service.StoreService;
import com.seowon.storereservationsystem.type.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;
    private final StoreService storeService;

    @PostMapping("/owner/register")
    public ResponseEntity<?> register(
            @RequestBody OwnerRegistrationDto registrationDto) {
        Owner owner = ownerService.register(registrationDto);
        return ResponseEntity.ok(owner);
    }

    @GetMapping("/owner/login")
    public String showOwnerLoginPage() {
        return "ownerLogin"; // ownerLogin.html을 렌더링
    }


    @GetMapping("/owner/login-success")
    public String ownerLoginSuccess() {
        return "login Success";
    }

        // 점주의 개인 정보 조회
    // 세션.. 토큰..이 필요한 부분
    @GetMapping("/owner/profile")
    public ResponseEntity<?> userProfile() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        System.out.println(currentUserName);
        // 사용자 정보를 기반으로 필요한 로직 처리
        return null;
    }

    // 점주의 회원 탈퇴
    @DeleteMapping("/owner/resign")
    public ResponseEntity<?> resign(
            @RequestBody LoginInput loginInput) {
        boolean deleted = ownerService.deleteOwner(loginInput);
        return ResponseEntity.ok("회원 탈퇴를 완료하였습니다.");
    }

    // 점주의 매장 생성 (Create)
    @GetMapping("/owner/addStore")
    public ResponseEntity<?> addStore(
            @RequestBody StoreRegistrationDto registrationDto) {
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
    @PostMapping("/owner/store-list")
    public ResponseEntity<?> showStoreList(
            @RequestBody OwnerRegistrationDto registrationDto) {
        Owner owner = ownerService.register(registrationDto);
        return ResponseEntity.ok(owner);
    }

    // 점주의 매장 정보 갱신 (Update)
    @PutMapping("/owner/update-store")
    public ResponseEntity<?> updateStore(
            @RequestBody OwnerRegistrationDto registrationDto) {
        Owner owner = ownerService.register(registrationDto);
        return ResponseEntity.ok(owner);
    }

    // 점주의 매장 삭제 (Delete)
    @DeleteMapping("/owner/delete-store")
    public ResponseEntity<?> deleteStore(
            @RequestBody OwnerRegistrationDto registrationDto) {
        Owner owner = ownerService.register(registrationDto);
        return ResponseEntity.ok(owner);
    }

}
