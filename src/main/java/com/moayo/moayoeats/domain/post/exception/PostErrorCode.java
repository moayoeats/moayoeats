package com.moayo.moayoeats.domain.post.exception;

import com.moayo.moayoeats.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {

    //403
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN.value(), "작성자만 글을 수정/삭제할 수 있습니다."),
    //404
    NOT_FOUND_POST(HttpStatus.NOT_FOUND.value(), "글을 찾지 못하였습니다.");

    private final int httpStatus;
    private final String message;

}
