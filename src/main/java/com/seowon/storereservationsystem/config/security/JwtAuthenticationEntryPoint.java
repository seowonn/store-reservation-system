package com.seowon.storereservationsystem.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seowon.storereservationsystem.dto.ErrorResponseDto;
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
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                UNAUTHORIZED_USER, UNAUTHORIZED_USER.getDescription()
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // ErrorResponseDto를 JSON 형태로 변환하여 응답에 쓰기
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }
}

