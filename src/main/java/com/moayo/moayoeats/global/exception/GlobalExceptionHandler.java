package com.moayo.moayoeats.global.exception;

import com.moayo.moayoeats.global.dto.ApiResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ApiResponse<?> handleGlobalException(GlobalException globalException) {
        ErrorCode errorCode = globalException.getErrorCode();
        return new ApiResponse<>(errorCode.getHttpStatus(), errorCode.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<List<String>> handleMethodArgumentNotValidException(
        BindingResult bindingResult) {
        List<String> errors = bindingResult.getFieldErrors().stream()
            .map((FieldError fieldError) -> fieldError.getField() +" "+ fieldError.getDefaultMessage())
            .toList();
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "입력값이 잘못되었습니다", errors);
    }

}
