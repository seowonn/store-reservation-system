package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.config.security.JwtTokenProvider;
import com.seowon.storereservationsystem.dto.LoginRequest;
import com.seowon.storereservationsystem.dto.LoginResponse;
import com.seowon.storereservationsystem.dto.OwnerRegistrationDto;
import com.seowon.storereservationsystem.dto.UserRegistrationDto;
import com.seowon.storereservationsystem.entity.Owner;
import com.seowon.storereservationsystem.entity.User;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.OwnerRepository;
import com.seowon.storereservationsystem.repository.UserRepository;
import com.seowon.storereservationsystem.service.AuthService;
import com.seowon.storereservationsystem.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import static com.seowon.storereservationsystem.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public User registerUser(UserRegistrationDto registrationDto) {
        // 1. 이 회원이 이미 가입된 사람인지 확인
        Optional<User> optionalUser =
                userRepository.findByUserId(registrationDto.getUserId());

        if(optionalUser.isPresent()) {
            throw new ReservationSystemException(ALREADY_REGISTERED_USER);
        }

        // 2. password encoding 후 user 테이블에 저장
        String encPassword =
                BCrypt.hashpw(registrationDto.getPassword(), BCrypt.gensalt());

        User user = User.builder()
                .userId(registrationDto.getUserId())
                .name(registrationDto.getName())
                .phone(registrationDto.getPhone())
                .password(encPassword)
                .role(Role.USER)
                .build();

        // 3. 가입 성공한 User 객체 반환
        return userRepository.save(user);
    }

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
        String accessToken =
                jwtTokenProvider.createAccessToken(loginRequest.getUsername(),
                        Collections.singletonList(Role.USER.getRole()));
        String refreshToken =
                jwtTokenProvider.createRefreshToken(loginRequest.getUsername(),
                        Collections.singletonList(Role.USER.getRole()));

        return LoginResponse.builder()
                .message("로그인에 성공하였습니다.")
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();
    }

    @Override
    public Owner registerOwner(OwnerRegistrationDto registrationDto) {
        // 1. 이 회원이 이미 가입된 사람인지 확인
        Optional<Owner> byOwnerId =
                ownerRepository.findByOwnerId(registrationDto.getOwnerId());

        if(byOwnerId.isPresent()){
            throw new ReservationSystemException(ALREADY_REGISTERED_USER);
        }

        // 2. password encoding 후 owner 테이블에 저장
        String encPassword =
                BCrypt.hashpw(registrationDto.getPassword(), BCrypt.gensalt());

        Owner owner = Owner.builder()
                .ownerId(registrationDto.getOwnerId())
                .name(registrationDto.getOwnerName())
                .phone(registrationDto.getPhone())
                .password(encPassword)
                .role(Role.OWNER)
                .build();

        // 3. 가입 성공한 Owner 객체 반환
        return ownerRepository.save(owner);
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
        String accessToken =
                jwtTokenProvider.createAccessToken(loginRequest.getUsername(),
                        Collections.singletonList(Role.OWNER.getRole()));

        String refreshToken =
                jwtTokenProvider.createRefreshToken(loginRequest.getUsername(),
                        Collections.singletonList(Role.OWNER.getRole()));

        return LoginResponse.builder()
                .message("로그인에 성공하였습니다.")
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();
    }

}
