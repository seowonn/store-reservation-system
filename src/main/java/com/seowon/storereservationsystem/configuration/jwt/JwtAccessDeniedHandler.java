package com.seowon.storereservationsystem.configuration.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static com.seowon.storereservationsystem.type.ErrorCode.ACCESS_DENIED;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {
        response.sendError(
                HttpServletResponse.SC_FORBIDDEN,
                ACCESS_DENIED.getDescription()
        );
    }
}
