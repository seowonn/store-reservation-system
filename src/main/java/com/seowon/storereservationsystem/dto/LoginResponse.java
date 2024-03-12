package com.seowon.storereservationsystem.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String message;
    private String accessToken;
    private String refreshToken;
}
