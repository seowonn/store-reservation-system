package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.ErrorResponseDto;
import com.seowon.storereservationsystem.dto.LoginRequest;
import com.seowon.storereservationsystem.dto.LoginResponse;
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

    @PostMapping("/user")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/owner")
    public ResponseEntity<?> authenticateOwner(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = authService.authenticateOwner(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/login-fail")
    public void LoginFail(HttpServletRequest request) {
        Object error =
                request.getSession().getAttribute("error");

        if(error instanceof ErrorCode) {
            throw new ReservationSystemException(((ErrorCode) error));
        }
    }

    @GetMapping("/login-success")
    public ResponseEntity<?> LoginSuccess() {
        return ResponseEntity.ok("로그인에 성공하였습니다.");
    }

    @GetMapping("/logout-success")
    public ResponseEntity<?>  LogoutSuccess() {
        return ResponseEntity.ok("로그아웃에 성공하였습니다.");}

    @GetMapping("/error")
    public ResponseEntity<?> error() {
        ErrorResponseDto responseDto =
                new ErrorResponseDto(UNREGISTERED_USER,
                        UNAUTHORIZED_USER.getDescription());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
    }
}
