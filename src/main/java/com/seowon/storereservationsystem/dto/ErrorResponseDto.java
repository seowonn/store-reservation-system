package com.seowon.storereservationsystem.dto;

import com.seowon.storereservationsystem.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {
    private ErrorCode errorCode;
    private String errorMessage;
}
