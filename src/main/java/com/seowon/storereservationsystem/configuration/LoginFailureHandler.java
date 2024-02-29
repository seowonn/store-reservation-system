package com.seowon.storereservationsystem.configuration;

import com.seowon.storereservationsystem.type.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

import static com.seowon.storereservationsystem.type.ErrorCode.*;

@Configuration
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        ErrorCode errorCode;
        if(exception instanceof BadCredentialsException){
            errorCode = UNMATCHED_LOGIN_FORM;
        } else if (exception instanceof UsernameNotFoundException) {
            errorCode = UNREGISTERED_USER;
        } else {
            errorCode = INVALID_SERVER_ERROR;
        }

        request.getSession().setAttribute("error", errorCode);
        response.sendRedirect("/error/login-fail");
    }
}
