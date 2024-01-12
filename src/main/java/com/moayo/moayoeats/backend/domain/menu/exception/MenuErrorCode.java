package com.moayo.moayoeats.backend.domain.menu.exception;

import com.moayo.moayoeats.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MenuErrorCode implements ErrorCode {

    //403
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN.value(), "허락되지 않은 접근입니다."),

    // 404
    NOT_FOUND_MENU(HttpStatus.NOT_FOUND.value(), "메뉴를 찾을 수 없습니다.");

    private final int httpStatus;
    private final String message;
}
