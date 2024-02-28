package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.LoginInput;
import com.seowon.storereservationsystem.dto.UserRegistrationDto;
import com.seowon.storereservationsystem.entity.User;
import com.seowon.storereservationsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 사용자의 회원가입
    @PostMapping("/user/register")
    public ResponseEntity<?> register(
            @RequestBody UserRegistrationDto registrationDto) {
        User user = userService.register(registrationDto);
        return ResponseEntity.ok(user);
    }

    // 사용자 로그인
    // Spring Security 가 가로채서 검증함.
    @PostMapping("/user/login")
    public ResponseEntity<?> login(
            @RequestBody LoginInput loginInput) {
        userService.login(loginInput);
        return ResponseEntity.ok("로그인에 성공하였습니다.");
    }

    @GetMapping("/user/aa")
    public ResponseEntity<?> user(
            @AuthenticationPrincipal UserDetails userDetails
    ){
        return new ResponseEntity<>(userDetails.getUsername(), HttpStatus.OK);
    }

    // 사용자의 개인 정보 조회
    @GetMapping("/user/profile")
    public ResponseEntity<?> userProfile(@AuthenticationPrincipal UserDetails auserDetails) {
        return ResponseEntity.ok(null);
    }

    // 사용자의 개인 정보 변경
    // 세션.. 토큰..이 필요한 부분
    @PutMapping("/user/update-user")
    public ResponseEntity<?> updateUser(
            @RequestBody UserRegistrationDto registrationDto) {
        User user = userService.register(registrationDto);
        return ResponseEntity.ok(user);
    }

    // 사용자의 회원 탈퇴
    @DeleteMapping("/user/resign")
    public ResponseEntity<?> resign(
            @RequestBody UserRegistrationDto registrationDto) {
        User user = userService.register(registrationDto);
        return ResponseEntity.ok(user);
    }

}
