package com.moayo.moayoeats.domain.user.exception;

import com.moayo.moayoeats.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    // 400
    ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 회원입니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다."),

    // 404
    NOT_EXIST_USER(HttpStatus.NOT_FOUND.value(), "존재하지 않는 사용자입니다.");

    private final int httpStatus;
    private final String message;
}
