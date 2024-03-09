package com.seowon.storereservationsystem.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // 회원가입, 로그인 등 인증이 필요 없는 경로에 대한 처리
        String requestURI = request.getRequestURI();
        if ("/user/register".equals(requestURI) || "/api/auth/user".equals(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        } else if ("/owner/register".equals(requestURI) || "/api/auth/owner/login".equals(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtTokenProvider.resolveToken(request);
        LOGGER.info("[doFilterInternal] token 값 추출 완료. token : {}", token);

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LOGGER.info("[doFilterInternal] Security Context에 '{}' 인증 정보를 저장했습니다. uri: {}",
                    authentication.getName(), requestURI);
        } else {
            LOGGER.info("[doFilterInternal] 유효한 JWT 토큰이 없습니다. uri: {}", requestURI);
        }

        filterChain.doFilter(request, response);
    }

}
