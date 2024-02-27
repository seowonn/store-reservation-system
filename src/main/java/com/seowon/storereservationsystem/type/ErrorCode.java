package com.seowon.storereservationsystem.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ALREADY_REGISTERED_USER("이미 등록된 사용자입니다."),
    INVALID_SERVER_ERROR("내부 서버 오류가 발생하였습니다.");

    private final String description;
}
