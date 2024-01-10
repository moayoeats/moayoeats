package com.moayo.moayoeats.domain.userpost.exception;

import com.moayo.moayoeats.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserPostErrorCode implements ErrorCode {

    // 404
    NOT_FOUND_USERPOST(HttpStatus.NOT_FOUND.value(), "해당 글과 사용자와의 관계를 찾을 수 없습니다.");

    private final int httpStatus;
    private final String message;
}
