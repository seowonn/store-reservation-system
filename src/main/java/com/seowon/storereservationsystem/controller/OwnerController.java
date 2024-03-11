package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.LoginRequest;
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
     * 점주의 개인 정보 조회
     */
    @GetMapping("/profile")
    public ResponseEntity<?> getOwner() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        Owner ownerProfile =
                ownerService.getOwnerProfile(authentication.getName());
        return ResponseEntity.ok(ownerProfile);
    }

    /**
     * 점주의 개인 정보 변경
     * @RequestBody registrationDto
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateOwner(
            @RequestBody OwnerRegistrationDto registrationDto) {
        String ownerId =
                SecurityContextHolder.getContext().getAuthentication().getName();
        ownerService.updateOwner(registrationDto, ownerId);
        return ResponseEntity.ok(registrationDto);
    }

    /**
     * 점주의 회원 탈퇴
     * @RequestBody loginInput
     */
    @DeleteMapping("/resign")
    public ResponseEntity<?> resign(
            @RequestBody LoginRequest loginRequest) {
        ownerService.deleteOwner(loginRequest);
        return ResponseEntity.ok("회원 탈퇴를 완료하였습니다.");
    }
}
