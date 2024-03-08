package com.seowon.storereservationsystem.configuration;

import com.seowon.storereservationsystem.configuration.jwt.JwtAccessDeniedHandler;
import com.seowon.storereservationsystem.configuration.jwt.JwtAuthenticationEntryPoint;
import com.seowon.storereservationsystem.configuration.jwt.JwtAuthenticationFilter;
import com.seowon.storereservationsystem.configuration.jwt.JwtTokenProvider;
import com.seowon.storereservationsystem.service.UserDetailService;
import com.seowon.storereservationsystem.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Order(1)
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class UserSecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder userPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(
                                        new JwtAuthenticationEntryPoint())
                                .accessDeniedHandler(
                                        new JwtAccessDeniedHandler())
                )
                .sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                )
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests
                            .requestMatchers("/user/register",
                                    "/user/login", "/login-fail",
                                    "/logout-success", "/login-success")
                            .permitAll();

                    authorizeRequests.
                            requestMatchers("/user/**").authenticated();

                    authorizeRequests
                            .requestMatchers("/review/delete/**")
                            .hasAnyRole(
                                    Role.USER.getRole(),
                                    Role.OWNER.getRole()
                            );

                })
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/logout-success")
                );
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/h2-condole/**");
    }

}
