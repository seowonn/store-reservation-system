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
    ALREADY_REGISTERED_STORE("이미 등록된 매장입니다."),
    UNREGISTERED_STORE("존재하지 않는 매장입니다."),
    UNREGISTERED_RESERVATION("예약 내역이 존재하지 않습니다."),
    VISITED_UNCHECK("예약은 하였으나 방문 기록이 존재하지 않습니다."),
    EXPIRED_RESERVATION("예약 시간 내에 오지 않아 예약이 취소되었습니다. 다시 예약해주세요."),
    DENIED_RESERVATION("승인되지 않은 예약입니다. 다시 예약해주세요."),
    NO_REVIEW("작성된 리뷰내역이 없습니다."),
    ACCESS_DENIED("해당 페이지에 대한 접근 권한이 없는 사용자입니다."),
    ALREADY_RESERVED("이미 예약을 진행하였습니다. 다시 예약 시 이전 예약을 취소하고 이용해주세요."),
    IMPOSSIBLE_RESERVE_TIME("해당 시간대로 예약을 진행할 수 없습니다."),
    SEND_EMAIL_ERROR("이메일 전송 과정 중 문제가 발생하였습니다. 다시 전송해주세요."),
    INVALID_SERVER_ERROR("내부 서버 오류가 발생하였습니다.");

    private final String description;
}
