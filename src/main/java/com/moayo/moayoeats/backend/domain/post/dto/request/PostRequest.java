package com.moayo.moayoeats.backend.domain.post.dto.request;

import com.moayo.moayoeats.backend.domain.post.entity.CategoryEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostRequest(

    @NotBlank String address,
    @NotBlank String store,
    @NotNull Integer minPrice,
    @NotNull Integer deliveryCost,

    @NotNull @Max(59) Integer deadlineMins,
    @NotNull @Max(3) Integer deadlineHours,
    CategoryEnum category

) {

}
