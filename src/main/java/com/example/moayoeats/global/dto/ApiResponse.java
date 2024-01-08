package com.example.moayoeats.global.dto;

import lombok.Builder;

@Builder
public record ApiResponse<T>(
    int status,
    String message,
    T data
) {

}

