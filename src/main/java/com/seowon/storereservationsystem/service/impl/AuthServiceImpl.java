package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.config.security.JwtTokenProvider;
import com.seowon.storereservationsystem.dto.LoginRequest;
import com.seowon.storereservationsystem.dto.LoginResponse;
import com.seowon.storereservationsystem.entity.Owner;
import com.seowon.storereservationsystem.entity.User;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.OwnerRepository;
import com.seowon.storereservationsystem.repository.UserRepository;
import com.seowon.storereservationsystem.service.AuthService;
import com.seowon.storereservationsystem.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.seowon.storereservationsystem.type.ErrorCode.UNMATCHED_PASSWORD;
import static com.seowon.storereservationsystem.type.ErrorCode.UNREGISTERED_USER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        // 1. username(userId)를 가지고 해당 사용자가 회원가입된 사람인지 확인
        User user = userRepository.findByUserId(loginRequest.getUsername())
                .orElseThrow(() ->
                        new ReservationSystemException(UNREGISTERED_USER));

        // 2. 로그인 시 입력한 비밀번호가 전에 등록된 비밀번호와 일치하는지 확인
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches =
                passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if(!matches){
            throw new ReservationSystemException(UNMATCHED_PASSWORD);
        }

        // 3. 인증된 사용자에 대한 JWT 토큰을 생성
        String token =
                jwtTokenProvider.createToken(loginRequest.getUsername(),
                        Collections.singletonList(Role.USER.getRole()));

        return LoginResponse.builder()
                .message("로그인에 성공하였습니다.")
                .token(token).build();
    }

    @Override
    public LoginResponse authenticateOwner(LoginRequest loginRequest) {
        // 1. username(ownerId)를 가지고 해당 사용자가 회원가입된 사람인지 확인
        Owner owner = ownerRepository.findByOwnerId(loginRequest.getUsername())
                .orElseThrow(() ->
                        new ReservationSystemException(UNREGISTERED_USER));

        // 2. 로그인 시 입력한 비밀번호가 전에 등록된 비밀번호와 일치하는지 확인
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches =
                passwordEncoder.matches(loginRequest.getPassword(), owner.getPassword());
        if(!matches){
            throw new ReservationSystemException(UNMATCHED_PASSWORD);
        }

        // 3. 인증된 사용자에 대한 JWT 토큰을 생성
        String token =
                jwtTokenProvider.createToken(loginRequest.getUsername(),
                        Collections.singletonList(Role.OWNER.getRole()));

        return LoginResponse.builder()
                .message("로그인에 성공하였습니다.")
                .token(token).build();
    }

}
