package com.moayo.moayoeats.backend.domain.post.exception;

import com.moayo.moayoeats.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {

    //400
    POST_ALREADY_CLOSED(HttpStatus.BAD_REQUEST.value(), "이미 모집이 마감된 게시글 입니다."),
    MENU_NOT_ALLOWED(HttpStatus.BAD_REQUEST.value(), "모집마감 후에는 메뉴를 생성/삭제할 수 없습니다."),

    // 401
    UNAUTHORIZED_USER_ABOUT_POST(HttpStatus.UNAUTHORIZED.value(), "해당 글에 대한 권한이 없습니다."),

    //403
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN.value(), "작성자만 할 수 있는 기능입니다."),

    //404
    NOT_FOUND_POST(HttpStatus.NOT_FOUND.value(), "글을 찾을 수 없습니다.");

    private final int httpStatus;
    private final String message;

}
