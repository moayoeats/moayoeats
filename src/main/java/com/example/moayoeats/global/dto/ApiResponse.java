package com.example.moayoeats.global.dto;

import lombok.Builder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    public ApiResponse(int status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public ApiResponse(int status, String message){
        this.status = status;
        this.message = message;
        this.data = null;
    }
    public ApiResponse(T data){
        this.data = data;
    }
}

