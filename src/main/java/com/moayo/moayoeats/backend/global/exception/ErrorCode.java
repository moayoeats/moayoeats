package com.moayo.moayoeats.backend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public interface ErrorCode {

    public int getHttpStatus();
    public String getMessage();
}
