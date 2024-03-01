package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.LoginInput;
import com.seowon.storereservationsystem.dto.OwnerRegistrationDto;
import com.seowon.storereservationsystem.entity.Owner;
import com.seowon.storereservationsystem.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner")
public class OwnerController {
    private final OwnerService ownerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody OwnerRegistrationDto registrationDto) {
        Owner owner = ownerService.register(registrationDto);
        return ResponseEntity.ok(owner);
    }

    @GetMapping("/login")
    public String showOwnerLoginPage() {
        return "ownerLogin";
    }

        // 점주의 개인 정보 조회
    // 세션.. 토큰..이 필요한 부분
    @GetMapping("/profile")
    public ResponseEntity<?> userProfile() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        System.out.println(currentUserName);
        // 사용자 정보를 기반으로 필요한 로직 처리
        return null;
    }

    // 점주의 회원 탈퇴
    @DeleteMapping("/resign")
    public ResponseEntity<?> resign(
            @RequestBody LoginInput loginInput) {
        boolean deleted = ownerService.deleteOwner(loginInput);
        return ResponseEntity.ok("회원 탈퇴를 완료하였습니다.");
    }
}
