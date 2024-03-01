package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.UserRegistrationDto;
import com.seowon.storereservationsystem.entity.User;
import com.seowon.storereservationsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody UserRegistrationDto registrationDto) {
        User user = userService.register(registrationDto);
        return ResponseEntity.ok(user);
    }


    /**
     사용자 로그인
     Spring Security 가 가로채서 대신 검증함.
     **/
    @GetMapping("/login")
    public String showUserLoginPage() {
        return "userLogin";
    }

    // 사용자의 개인 정보 조회
    @GetMapping("/profile")
    public ResponseEntity<?> userProfile(@AuthenticationPrincipal UserDetails auserDetails) {
        return ResponseEntity.ok(null);
    }

    // 사용자의 개인 정보 변경
    // 세션.. 토큰..이 필요한 부분
    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(
            @RequestBody UserRegistrationDto registrationDto) {
        User user = userService.register(registrationDto);
        return ResponseEntity.ok(user);
    }

    // 사용자의 회원 탈퇴
    @DeleteMapping("/resign")
    public ResponseEntity<?> resign(
            @RequestBody UserRegistrationDto registrationDto) {
        User user = userService.register(registrationDto);
        return ResponseEntity.ok(user);
    }

}
