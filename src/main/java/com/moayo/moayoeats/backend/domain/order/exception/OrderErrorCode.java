package com.moayo.moayoeats.backend.domain.order.exception;

import com.moayo.moayoeats.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    //403
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN.value(), "자신이 작성한 주문기록에 대해서만 리뷰를 남길 수 있습니다."),

    //404
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "주문내역을 찾을 수 없습니다.");

    private final int httpStatus;
    private final String message;
}
