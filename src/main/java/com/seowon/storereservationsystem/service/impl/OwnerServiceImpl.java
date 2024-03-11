package com.seowon.storereservationsystem.service.impl;

import com.seowon.storereservationsystem.dto.LoginRequest;
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

import static com.seowon.storereservationsystem.type.ErrorCode.ALREADY_REGISTERED_USER;
import static com.seowon.storereservationsystem.type.ErrorCode.UNREGISTERED_USER;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    @Override
    public Owner getOwnerProfile(String ownerId) {
        Owner foundOwner = ownerRepository.findByOwnerId(ownerId)
                .orElseThrow(() ->
                        new ReservationSystemException(UNREGISTERED_USER));
        return Owner.builder()
                .name(foundOwner.getName())
                .phone(foundOwner.getPhone())
                .ownerId(foundOwner.getOwnerId())
                .build();
    }

    @Override
    public void updateOwner(OwnerRegistrationDto registrationDto, String ownerId) {
        Owner foundOwner = ownerRepository.findByOwnerId(ownerId)
                .orElseThrow(() ->
                        new ReservationSystemException(UNREGISTERED_USER));

        String encPassword =
                BCrypt.hashpw(registrationDto.getPassword(), BCrypt.gensalt());

        foundOwner.setOwnerId(registrationDto.getOwnerId());
        foundOwner.setName(registrationDto.getOwnerName());
        foundOwner.setPhone(registrationDto.getPhone());
        foundOwner.setPassword(encPassword);
        ownerRepository.save(foundOwner);
    }

    @Override
    public void deleteOwner(LoginRequest loginRequest) {
        ownerRepository.findByOwnerId(loginRequest.getUsername())
                        .orElseThrow(() -> new ReservationSystemException(
                                UNREGISTERED_USER
                        ));
        ownerRepository.deleteByOwnerId(loginRequest.getUsername());;
    }
}
