package com.moayo.moayoeats.backend.domain.user.dto.response;

import lombok.Builder;

@Builder
public record AddressResponse(
    Double latitude,
    Double longitude
) {

}
