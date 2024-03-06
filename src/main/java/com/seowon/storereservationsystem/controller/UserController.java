package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.LoginInput;
import com.seowon.storereservationsystem.dto.UserRegistrationDto;
import com.seowon.storereservationsystem.entity.User;
import com.seowon.storereservationsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    /**
     * 사용자의 회원가입
     * userId(이메일)로 구분
     * @RequestBody registrationDto
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody UserRegistrationDto registrationDto) {
        User user = userService.register(registrationDto);
        return ResponseEntity.ok(user);
    }

    /**
     * 사용자의 개인 정보 조회
     */
    @GetMapping("/profile")
    public ResponseEntity<?> getUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        User userProfile = userService.getUserProfile(authentication.getName());
        return ResponseEntity.ok(userProfile);
    }

    /**
     * 사용자의 개인 정보 변경
     * @RequestBody registrationDto
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(
            @RequestBody UserRegistrationDto registrationDto) {
        String userId =
                SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateUser(registrationDto, userId);
        return ResponseEntity.ok(registrationDto);
    }

    /**
     * 사용자의 회원 탈퇴
     * @RequestBody loginInput
     */
    @DeleteMapping("/resign")
    public ResponseEntity<?> resign(
            @RequestBody LoginInput loginInput) {
        userService.deleteUser(loginInput);
        return ResponseEntity.ok("회원 탈퇴를 완료하였습니다.");
    }

    /**
     * 더 추가해볼 내용 : 사용자의 비밀번호 초기화 로직 (미완성)
     * @PathVariable id
     */
    @GetMapping("/{id}/password/reset")
    public ResponseEntity<?> resetUserPassword(@PathVariable Long id) {
        userService.resetUserPassword(id);
        return ResponseEntity.ok().build();
    }
}
