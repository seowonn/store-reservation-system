package com.seowon.storereservationsystem.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.seowon.storereservationsystem.type.ErrorCode.UNAUTHORIZED_USER;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);


    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        LOGGER.info("[commence] 인증 실패로 response.sendError 발생");
        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                UNAUTHORIZED_USER.getDescription()
        );
    }
}

