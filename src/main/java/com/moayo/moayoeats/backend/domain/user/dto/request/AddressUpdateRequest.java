package com.moayo.moayoeats.backend.domain.user.dto.request;

import lombok.Builder;

@Builder
public record AddressUpdateRequest(
    String address
) {

}
