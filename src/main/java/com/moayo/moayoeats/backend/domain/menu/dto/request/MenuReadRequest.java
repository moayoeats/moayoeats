package com.moayo.moayoeats.backend.domain.menu.dto.request;

import jakarta.validation.constraints.NotNull;

public record MenuReadRequest (
    @NotNull
    Long postId
){

}
