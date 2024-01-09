package com.moayo.moayoeats.domain.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MenuCreateRequest(
    @NotNull
    Long postId,
    @NotBlank
    String name,
    @NotNull
    Integer price
) {

}
