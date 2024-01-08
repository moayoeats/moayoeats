package com.moayo.moayoeats.domain.post;

import com.moayo.moayoeats.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {

    //404
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "글을 찾지 못하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
