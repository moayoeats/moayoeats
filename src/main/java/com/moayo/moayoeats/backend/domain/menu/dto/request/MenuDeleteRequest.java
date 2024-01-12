package com.moayo.moayoeats.backend.domain.menu.dto.request;

import jakarta.validation.constraints.NotNull;

public record MenuDeleteRequest(
    @NotNull
    Long menuId
){

}
