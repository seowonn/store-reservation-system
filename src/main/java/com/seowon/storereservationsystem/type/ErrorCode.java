package com.seowon.storereservationsystem.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ALREADY_REGISTERED_USER("이미 등록된 회원입니다."),
    UNREGISTERED_USER("등록되지 않은 회원입니다. 회원가입을 진행해주세요."),
    UNMATCHED_LOGIN_FORM("아이디와 비밀번호를 확인해주세요."),
    UNAUTHORIZED_USER("인증되지 않은 사용자입니다. 로그인을 진행해 주세요."),
    ALREADY_REGISTERED_STORE("이미 등록된 매장입니다."),
    UNREGISTERED_STORE("존재하지 않는 매장입니다."),
    INVALID_SERVER_ERROR("내부 서버 오류가 발생하였습니다.");

    private final String description;
}
