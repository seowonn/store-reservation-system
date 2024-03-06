package com.seowon.storereservationsystem.configuration;

import com.seowon.storereservationsystem.service.OwnerDetailService;
import com.seowon.storereservationsystem.type.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@Order(2)
//@Configuration
//@EnableWebSecurity
public class OwnerSecurityConfiguration {

    @Bean
    public PasswordEncoder ownerPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean UserDetailsService ownerDetailsService() {
        return new OwnerDetailService();
    }

    @Bean
    public DaoAuthenticationProvider ownerAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(ownerDetailsService());
        provider.setPasswordEncoder(ownerPasswordEncoder());
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    @Bean
    public SecurityFilterChain ownerFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(
                                        new CustomAuthenticationEntryPoint())
                )
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests
                            .requestMatchers("/owner/register",
                                    "/owner/login", "/error/login-fail",
                                    "/logout-success", "/login-success",
                                    "/error")
                            .permitAll();
                    authorizeRequests.
                            requestMatchers("/owner/**").authenticated();

                    authorizeRequests.
                            requestMatchers("/owner/**")
                            .hasRole(Role.OWNER.getRole());
                })
                .formLogin(formLogin -> formLogin
                        .loginPage("/owner/login")
                        .failureHandler(new LoginFailureHandler())
                        .loginProcessingUrl("/owner/login")
                        .defaultSuccessUrl("/login-success")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/owner/logout")
                        .logoutSuccessUrl("/logout-success")
                );
        return http.build();
    }
}
