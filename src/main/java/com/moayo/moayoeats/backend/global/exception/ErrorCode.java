package com.moayo.moayoeats.backend.global.exception;

public interface ErrorCode {

    public int getHttpStatus();

    public String getMessage();
}
