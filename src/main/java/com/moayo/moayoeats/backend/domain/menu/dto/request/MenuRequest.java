package com.moayo.moayoeats.backend.domain.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MenuRequest(
    @NotNull
    Long postId,
    @NotBlank
    String name,
    @NotNull
    Integer price
) {

}
