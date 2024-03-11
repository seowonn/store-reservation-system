package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.*;
import com.seowon.storereservationsystem.entity.Owner;
import com.seowon.storereservationsystem.entity.User;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.service.AuthService;
import com.seowon.storereservationsystem.type.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.seowon.storereservationsystem.type.ErrorCode.UNAUTHORIZED_USER;
import static com.seowon.storereservationsystem.type.ErrorCode.UNREGISTERED_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 사용자의 회원가입
     * userId(이메일)로 구분
     * @RequestBody registrationDto
     */
    @PostMapping("/user/register")
    public ResponseEntity<?> register(
            @RequestBody UserRegistrationDto registrationDto) {
        User user = authService.registerUser(registrationDto);
        return ResponseEntity.ok(user);
    }

    /**
     * 사용자의 로그인
     * @RequestBody loginRequest
     */
    @PostMapping("/user/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    /**
     * 점주의 회원가입
     * ownerId(이메일)로 구분
     * @RequestBody registrationDto
     */
    @PostMapping("/owner/register")
    public ResponseEntity<?> register(
            @RequestBody OwnerRegistrationDto registrationDto) {
        Owner owner = authService.registerOwner(registrationDto);
        return ResponseEntity.ok(owner);
    }

    /**
     * 점주의 로그인
     * @RequestBody loginRequest
     */
    @PostMapping("/owner/login")
    public ResponseEntity<?> authenticateOwner(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = authService.authenticateOwner(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
