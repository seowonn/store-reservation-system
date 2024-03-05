package com.seowon.storereservationsystem.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Result {
    SUCCESS("예약을 완료하였습니다. 예약 시간 10분 내로 와주시길 바랍니다."),
    FAIL("예약을 실패하였습니다. 다시 시도해주세요.");

    private final String result;
}
