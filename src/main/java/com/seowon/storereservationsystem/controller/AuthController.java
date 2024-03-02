package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.ErrorResponseDto;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.type.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.seowon.storereservationsystem.type.ErrorCode.UNAUTHORIZED_USER;
import static com.seowon.storereservationsystem.type.ErrorCode.UNREGISTERED_USER;

@RestController
public class AuthController {
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
