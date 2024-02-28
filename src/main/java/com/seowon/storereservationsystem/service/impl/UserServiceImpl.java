package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.LoginInput;
import com.seowon.storereservationsystem.dto.UserRegistrationDto;
import com.seowon.storereservationsystem.entity.User;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.UserRepository;
import com.seowon.storereservationsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.seowon.storereservationsystem.type.ErrorCode.*;
import static com.seowon.storereservationsystem.type.ErrorCode.UNMATCHED_PASSWORD;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User register(UserRegistrationDto registrationDto) {
        // 1. 이 회원이 이미 가입된 사람인지 확인
        boolean exists =
                userRepository.existsById(registrationDto.getUserId());
        if (exists) {
            throw new ReservationSystemException(ALREADY_REGISTERED_USER);
        }

        // 2. password encoding 후 owner 테이블에 저장
        String encPassword =
                BCrypt.hashpw(registrationDto.getPassword(), BCrypt.gensalt());

        User user = User.builder()
                .userId(registrationDto.getUserId())
                .name(registrationDto.getName())
                .phone(registrationDto.getPhone())
                .password(encPassword)
                .regAt(LocalDateTime.now())
                .build();

        // 3. 가입 성공한 Owner 객체 반환
        return userRepository.save(user);
    }

    @Override
    public User login(LoginInput loginInput) {
        // 1. 로그인을 시도한 점주가 기존 회원인지 체크
        Optional<User> optionalUser =
                userRepository.findById(loginInput.getUserId());

        if(optionalUser.isEmpty()) {
            throw new ReservationSystemException(UNREGISTERED_USER);
        }

        // 2. 입력한 비밀번호와 저장된 비밀번호가 일치하는지 확인
        if (!BCrypt.checkpw(loginInput.getPassword(),
                optionalUser.get().getPassword())) {
            throw new ReservationSystemException(UNMATCHED_PASSWORD);
        }

        // 3. 로그인 성공한 객체를 반환
        return optionalUser.get();
    }

    @Override
    public User getUserInfo(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ReservationSystemException(INVALID_SERVER_ERROR);
        }
        return optionalUser.get();
    }
}
