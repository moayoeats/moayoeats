package com.moayo.moayoeats.backend.domain.user.exception;

import com.moayo.moayoeats.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    // 400
    ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 회원입니다."),
    ALREADY_EXIST_USER_NICKNAME(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 닉네임입니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다."),

    // 401
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED.value(), "접근 권한이 없습니다."),

    // 404
    NOT_EXIST_USER(HttpStatus.NOT_FOUND.value(), "존재하지 않는 사용자입니다.");

    private final int httpStatus;
    private final String message;
}
