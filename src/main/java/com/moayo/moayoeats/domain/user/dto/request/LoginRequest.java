package com.moayo.moayoeats.domain.user.dto.request;

public record LoginRequest(
    String email,
    String password
) {

}
