package com.seowon.storereservationsystem.exception;

import com.seowon.storereservationsystem.config.security.JwtTokenProvider;
import com.seowon.storereservationsystem.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.seowon.storereservationsystem.type.ErrorCode.ACCESS_DENIED;
import static com.seowon.storereservationsystem.type.ErrorCode.INVALID_SERVER_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ReservationSystemException.class)
    public ResponseEntity<ErrorResponseDto> handleReservationSystemException(ReservationSystemException e) {
        LOGGER.error("[ReservationSystemException] : {}", e.getErrorCode());
        ErrorResponseDto errorResponseDto =
                new ErrorResponseDto(e.getErrorCode(), e.getErrorMessage());
        return new ResponseEntity<>(errorResponseDto, BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        LOGGER.error("[AccessDeniedException] : {}", ex.getMessage());
        ErrorResponseDto errorResponseDto =
                new ErrorResponseDto(ACCESS_DENIED, ACCESS_DENIED.getDescription());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handlerException(Exception e) {
        LOGGER.error("[Exception] : {}", e.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                INVALID_SERVER_ERROR,
                INVALID_SERVER_ERROR.getDescription()
        );
        return new ResponseEntity<>(errorResponseDto, INTERNAL_SERVER_ERROR);
    }
}
