package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.LoginInput;
import com.seowon.storereservationsystem.dto.UserRegistrationDto;
import com.seowon.storereservationsystem.entity.User;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.UserRepository;
import com.seowon.storereservationsystem.service.UserService;
import com.seowon.storereservationsystem.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.seowon.storereservationsystem.type.ErrorCode.*;

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
                .role(Role.USER)
                .build();

        // 3. 가입 성공한 Owner 객체 반환
        return userRepository.save(user);
    }

    @Override
    public User getUserInfo(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ReservationSystemException(INVALID_SERVER_ERROR));
    }
}
