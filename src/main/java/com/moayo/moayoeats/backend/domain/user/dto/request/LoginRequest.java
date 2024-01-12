package com.moayo.moayoeats.backend.domain.user.dto.request;

public record LoginRequest(
    String email,
    String password
) {

}
