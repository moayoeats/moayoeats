package com.moayo.moayoeats.backend.domain.user.dto.response;

public record LoginResponse(
    String accessToken,
    String refreshToken
) {

}
