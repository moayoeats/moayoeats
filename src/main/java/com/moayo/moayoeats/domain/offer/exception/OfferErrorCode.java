package com.moayo.moayoeats.domain.offer.exception;

import com.moayo.moayoeats.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OfferErrorCode implements ErrorCode {

    // 400
    ALREADY_APPLIED_PARTICIPATION(HttpStatus.BAD_REQUEST.value(), "이미 참가 신청을 한 상태입니다.");

    private final int httpStatus;
    private final String message;
}
