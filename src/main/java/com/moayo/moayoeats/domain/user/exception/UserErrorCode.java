package com.moayo.moayoeats.domain.user.exception;

import com.moayo.moayoeats.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    // 400
    ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 회원입니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
