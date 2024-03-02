package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.dto.ErrorResponseDto;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.type.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import static com.seowon.storereservationsystem.type.ErrorCode.UNAUTHORIZED_USER;
import static com.seowon.storereservationsystem.type.ErrorCode.UNREGISTERED_USER;

@RestController
public class AuthController {
    @GetMapping("/error/login-fail")
    public void LoginFail(
            HttpServletRequest request, Model model
    ) {
        Object error =
                request.getSession().getAttribute("error");

        if(error instanceof ErrorCode) {
            throw new ReservationSystemException(((ErrorCode) error));
        }
    }

    @GetMapping("/login-success")
    public String LoginSuccess() {
        return "login Success";
    }

    @GetMapping("/logout-success")
    public String LogoutSuccess() {return "logout Success";}

    @GetMapping("/error")
    public ResponseEntity<?> error() {
        ErrorResponseDto responseDto =
                new ErrorResponseDto(UNREGISTERED_USER,
                        UNAUTHORIZED_USER.getDescription());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
    }
}
