package com.seowon.storereservationsystem.exception;

import com.seowon.storereservationsystem.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserNoticeException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;

    public UserNoticeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
