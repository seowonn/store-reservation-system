package com.seowon.storereservationsystem.configuration;

import com.seowon.storereservationsystem.exception.ReservationSystemException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static com.seowon.storereservationsystem.type.ErrorCode.UNAUTHORIZED_USER;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/owner/register",
                                "/user/register").permitAll()
                        .requestMatchers("/owner/user-info", "owner/update-user",
                                "/owner/resign", "/owner/addStore",
                                "/owner/store-list", "/owner/update-store",
                                "/owner/delete-store",
                                "/user/profile", "/user/update-user",
                                "/user/resign").authenticated()
                        .anyRequest().permitAll()
                );

//                .formLogin(formLogin -> formLogin
//                        .loginPage("/owner/login").permitAll()
//                        .loginProcessingUrl("/owner/login") // 로그인 처리 경로 설정
//                );

        return http.build();
    }




}
