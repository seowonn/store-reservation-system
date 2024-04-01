package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.LoginRequest;
import com.seowon.storereservationsystem.dto.UserRegistrationDto;
import com.seowon.storereservationsystem.entity.User;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.ReservationRepository;
import com.seowon.storereservationsystem.repository.ReviewRepository;
import com.seowon.storereservationsystem.repository.UserRepository;
import com.seowon.storereservationsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.seowon.storereservationsystem.type.ErrorCode.REVIEW_EXIST;
import static com.seowon.storereservationsystem.type.ErrorCode.UNREGISTERED_USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public User getUserProfile(String userId) {
        User foundUser = userRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ReservationSystemException(UNREGISTERED_USER));
        return User.builder()
                .name(foundUser.getName())
                .phone(foundUser.getPhone())
                .userId(foundUser.getUserId())
                .build();
    }

    @Override
    public void updateUser(UserRegistrationDto registrationDto, String userId) {
        User foundUser = userRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ReservationSystemException(UNREGISTERED_USER));

        String encPassword =
                BCrypt.hashpw(registrationDto.getPassword(), BCrypt.gensalt());

        foundUser.setUserId(registrationDto.getUserId());
        foundUser.setName(registrationDto.getName());
        foundUser.setPhone(registrationDto.getPhone());
        foundUser.setPassword(encPassword);
        userRepository.save(foundUser);
    }

    @Override
    public void deleteUser(LoginRequest loginRequest) {
        User user = userRepository.findByUserId(loginRequest.getUsername())
                .orElseThrow(() -> new ReservationSystemException(
                        UNREGISTERED_USER
                ));

        Long reviewCnt = reviewRepository.countByUser(user);
        if(reviewCnt> 0) {
            throw new ReservationSystemException(REVIEW_EXIST);
        }

        userRepository.deleteByUserId(loginRequest.getUsername());;
    }

    @Override
    public void resetUserPassword(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ReservationSystemException(
                        UNREGISTERED_USER
                ));

        // 비밀번호 초기화
        String resetPassword = makeResetPassword();
        String encryptPassword = getEncryptPassword(resetPassword);
        user.setPassword(encryptPassword);
        userRepository.save(user);

        String message =
                String.format("[%s]님의 임시 비밀번호가 [%s]로 초기화 되었습니다.",
                        user.getName(), resetPassword);
        sendSMS(message);
    }

    @Override
    public void sendSMS(String message) {
        System.out.println("[문자메시지전송]");
        System.out.println(message);
    }

    private String makeResetPassword() {
        return UUID.randomUUID().toString()
                .replaceAll("-", "").substring(0, 10);
    }

    private String getEncryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}
