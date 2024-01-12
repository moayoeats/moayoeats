package com.moayo.moayoeats.backend.global.exception;

import com.moayo.moayoeats.backend.global.dto.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Getter
@RequiredArgsConstructor
public class GlobalException extends RuntimeException {

    private final ErrorCode errorCode;

    @ExceptionHandler(GlobalException.class)
    public ApiResponse<?> handleGlobalException(
        GlobalException globalException
    ) {

        ErrorCode errorCode = globalException.getErrorCode();
        return new ApiResponse<>(errorCode.getHttpStatus(), errorCode.getMessage());
    }

}
