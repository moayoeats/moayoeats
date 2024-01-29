package com.moayo.moayoeats.backend.domain.pushEvent.exception;

import com.moayo.moayoeats.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PushEventErrorCode implements ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버에 에러가 발생했습니다!");

    private final int httpStatus;
    private final String message;
}
