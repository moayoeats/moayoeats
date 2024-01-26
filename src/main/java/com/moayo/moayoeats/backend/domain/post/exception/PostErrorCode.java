package com.moayo.moayoeats.backend.domain.post.exception;

import com.moayo.moayoeats.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {

    //400
    CANNOT_CLOSE_AFTER_ORDERED(HttpStatus.BAD_REQUEST.value(), "주문완료 후에 모집취소를 할 수 없습니다."),
    LOWER_THAN_MINIMUM_PRICE(HttpStatus.BAD_REQUEST.value(), "최소주문금액보다 적으면 모집마감을 할 수 없습니다."),
    POST_ALREADY_CLOSED(HttpStatus.BAD_REQUEST.value(), "이미 모집이 마감된 게시글 입니다."),
    MENU_NOT_ALLOWED(HttpStatus.BAD_REQUEST.value(), "주문완료 후에는 메뉴를 생성/삭제할 수 없습니다."),
    CANNOT_AFTER_ORDERED(HttpStatus.BAD_REQUEST.value(), "주문완료 후에 수행할 수 없는 동작입니다."),
    CLOSE_FIRST(HttpStatus.BAD_REQUEST.value(), "모집마감이 먼저 완료되어야 합니다."),
    ORDER_FIRST(HttpStatus.BAD_REQUEST.value(), "주문완료인 게시글에서만 할 수 있습니다."),
    POST_ALREADY_COMPLETED_ORDER(HttpStatus.BAD_REQUEST.value(), "이미 주문완료가 된 게시글 입니다."),

    // 401
    UNAUTHORIZED_USER_ABOUT_POST(HttpStatus.UNAUTHORIZED.value(), "해당 글에 대한 권한이 없습니다."),

    //403
    FORBIDDEN_ACCESS_HOST(HttpStatus.FORBIDDEN.value(), "작성자만 할 수 있는 기능입니다."),
    FORBIDDEN_ACCESS_PARTICIPANT(HttpStatus.FORBIDDEN.value(), "참가자만 할 수 있는 기능입니다."),

    //404
    NOT_FOUND_POST(HttpStatus.NOT_FOUND.value(), "글을 찾을 수 없습니다.");

    private final int httpStatus;
    private final String message;

}
