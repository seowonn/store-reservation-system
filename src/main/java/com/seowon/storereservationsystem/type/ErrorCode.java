package com.seowon.storereservationsystem.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ALREADY_REGISTERED_USER("이미 등록된 회원입니다."),
    UNREGISTERED_USER("등록되지 않은 회원입니다. 회원가입을 진행해주세요."),
    UNMATCHED_PASSWORD("일치하지 않는 비밀번호입니다."),
    UNAUTHORIZED_USER("인증되지 않은 사용자입니다. 로그인을 진행해 주세요."),
    INVALID_SERVER_ERROR("내부 서버 오류가 발생하였습니다.");

    private final String description;
}
