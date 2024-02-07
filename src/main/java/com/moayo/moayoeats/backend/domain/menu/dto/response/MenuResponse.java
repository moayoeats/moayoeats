package com.moayo.moayoeats.backend.domain.menu.dto.response;

import lombok.Builder;

@Builder
public record MenuResponse(
    Long id,
    String menuname,
    Integer price
) {

}
