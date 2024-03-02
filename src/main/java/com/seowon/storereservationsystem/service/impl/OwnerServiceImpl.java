package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.LoginInput;
import com.seowon.storereservationsystem.dto.OwnerRegistrationDto;
import com.seowon.storereservationsystem.entity.Owner;
import com.seowon.storereservationsystem.exception.ReservationSystemException;
import com.seowon.storereservationsystem.repository.OwnerRepository;
import com.seowon.storereservationsystem.service.OwnerService;
import com.seowon.storereservationsystem.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.seowon.storereservationsystem.entity.Owner.builder;
import static com.seowon.storereservationsystem.type.ErrorCode.ALREADY_REGISTERED_USER;
import static com.seowon.storereservationsystem.type.ErrorCode.UNREGISTERED_USER;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    @Override
    public Owner register(OwnerRegistrationDto registrationDto) {
        // 1. 이 회원이 이미 가입된 사람인지 확인
        Optional<Owner> byOwnerId =
                ownerRepository.findByOwnerId(registrationDto.getOwnerId());

        if(byOwnerId.isPresent()){
            throw new ReservationSystemException(ALREADY_REGISTERED_USER);
        }

        // 2. password encoding 후 owner 테이블에 저장
        String encPassword =
                BCrypt.hashpw(registrationDto.getPassword(), BCrypt.gensalt());

        Owner owner = builder()
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
    public Owner selectOwnerProfile(String ownerId) {
        Owner foundOwner = ownerRepository.findByOwnerId(ownerId)
                .orElseThrow(() ->
                        new ReservationSystemException(UNREGISTERED_USER));
        return Owner.builder()
                .name(foundOwner.getName())
                .phone(foundOwner.getPhone())
                .ownerId(foundOwner.getOwnerId())
                .password(foundOwner.getPassword())
                .build();
    }

    @Override
    public Owner updateOwner() {
        return null;
    }

    @Override
    public void deleteOwner(LoginInput loginInput) {
        ownerRepository.deleteByOwnerId(loginInput.getUserId());;
    }
}
