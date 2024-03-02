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

    /**
     * 점주의 회원가입
     * @RequestBody registrationDto
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody OwnerRegistrationDto registrationDto) {
        Owner owner = ownerService.register(registrationDto);
        return ResponseEntity.ok(owner);
    }

    /**
     * 점주의 등록 정보 조회
     */
    @GetMapping("/profile")
    public ResponseEntity<?> showOwnerProfile() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        Owner owner = ownerService.selectOwnerProfile(authentication.getName());
        return ResponseEntity.ok(owner);
    }

    /**
     * 점주의 회원 탈퇴
     * @RequestBody loginInput
     */
    @DeleteMapping("/resign")
    public ResponseEntity<?> resign(
            @RequestBody LoginInput loginInput) {
        ownerService.deleteOwner(loginInput);
        return ResponseEntity.ok("회원 탈퇴를 완료하였습니다.");
    }
}
