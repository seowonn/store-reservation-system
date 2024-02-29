package com.seowon.storereservationsystem.controller;

import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.type.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/logout-success")
    public String LogoutSuccess() {return "logout Success";}
}
