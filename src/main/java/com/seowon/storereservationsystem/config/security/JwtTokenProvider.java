package com.seowon.storereservationsystem.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService;

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1 hour
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 1 week
    private static final String KEY_ROLES = "roles";
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * 토큰 생성 (발급)
     */
    public String createAccessToken(String username, List<String> roles){
        LOGGER.info("[createAccessToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_ROLES, roles);

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(key)
                .compact();

        LOGGER.info("[createAccessToken] 토큰 생성 완료");
        return accessToken;
    }

    public String createRefreshToken(String username, List<String> roles){
        LOGGER.info("[createRefreshToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_ROLES, roles);

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME);

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(key)
                .compact();

        LOGGER.info("[createRefreshToken] 토큰 생성 완료");
        return refreshToken;
    }

    public Authentication getAuthentication(String token) {
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        List<String> roles = getRoleFromToken(token);

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(roles.get(0) + " " + this.getUsername(token));

        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}",
                userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    public List<String> getRoleFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("roles", List.class );
    }

    private String getUsername(String token) {
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        String info = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}",
                info);
        return info;
    }

    public String resolveToken(HttpServletRequest request) {
        LOGGER.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        String bearerToken = request.getHeader("Authorization");
        // 헤더가 "Bearer "로 시작하는 경우, 토큰 부분만 추출
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후 부분을 반환
        }
        return null; // 조건에 맞지 않는 경우 null 반환
    }

    public boolean validateToken(String token) {
        LOGGER.info("[validateToken] 토큰 유효 체크 시작");
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException
                 | MalformedJwtException e) {
            LOGGER.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            LOGGER.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e){
            LOGGER.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            LOGGER.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

}
