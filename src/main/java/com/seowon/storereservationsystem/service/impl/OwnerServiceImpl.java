package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.LoginInput;
import com.seowon.storereservationsystem.dto.OwnerRegistrationDto;
import com.seowon.storereservationsystem.entity.Owner;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.OwnerRepository;
import com.seowon.storereservationsystem.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.seowon.storereservationsystem.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    @Override
    public Owner register(OwnerRegistrationDto registrationDto) {
        // 1. 이 회원이 이미 가입된 사람인지 확인
        boolean exists =
                ownerRepository.existsById(registrationDto.getOwnerId());
        if (exists) {
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
                .createdAt(LocalDateTime.now())
                .build();
        System.out.println(registrationDto.getOwnerId());

        // 3. 가입 성공한 Owner 객체 반환
        return ownerRepository.save(owner);
    }

    @Override
    public Owner login(LoginInput loginInput) {
        // 1. 로그인을 시도한 점주가 기존 회원인지 체크
        Optional<Owner> optionalOwner =
                ownerRepository.findById(loginInput.getMemberId());

        if(optionalOwner.isEmpty()) {
            throw new ReservationSystemException(UNREGISTERED_USER);
        }

        // 2. 입력한 비밀번호와 저장된 비밀번호가 일치하는지 확인
        if (!BCrypt.checkpw(loginInput.getPassword(),
                optionalOwner.get().getPassword())) {
            throw new ReservationSystemException(UNMATCHED_PASSWORD);
        }

        // 3. 로그인 성공한 객체를 반환
        return optionalOwner.get();
    }
}
