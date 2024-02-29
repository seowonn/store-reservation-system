package com.seowon.storereservationsystem.exception;

import com.seowon.storereservationsystem.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReservationSystemException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;

    public ReservationSystemException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
