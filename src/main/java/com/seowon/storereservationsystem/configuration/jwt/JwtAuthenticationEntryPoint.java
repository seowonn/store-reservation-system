package com.seowon.storereservationsystem.configuration.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static com.seowon.storereservationsystem.type.ErrorCode.UNAUTHORIZED_USER;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                UNAUTHORIZED_USER.getDescription()
        );
    }
}

