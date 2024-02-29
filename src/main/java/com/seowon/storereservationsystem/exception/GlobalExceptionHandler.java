package com.seowon.storereservationsystem.exception;

import com.seowon.storereservationsystem.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.seowon.storereservationsystem.type.ErrorCode.INVALID_SERVER_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ReservationSystemException.class)
    public ResponseEntity<ErrorResponseDto> handleReservationSystemException(ReservationSystemException e) {
        log.error("{} is occurred.", e.getErrorCode());
        ErrorResponseDto errorResponseDto =
                new ErrorResponseDto(e.getErrorCode(), e.getErrorMessage());
        return new ResponseEntity<>(errorResponseDto, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handlerException(Exception e) {
        log.error("Exception is occurred.", e);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                INVALID_SERVER_ERROR,
                INVALID_SERVER_ERROR.getDescription()
        );
        return new ResponseEntity<>(errorResponseDto, INTERNAL_SERVER_ERROR);
    }
}
