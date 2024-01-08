package com.moayo.moayoeats.domain.post.dto.request;

import com.moayo.moayoeats.domain.menu.entity.Menu;
import com.moayo.moayoeats.domain.post.entity.CategoryEnum;
import com.moayo.moayoeats.domain.post.entity.DeadlineEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PostRequest(

    @NotBlank
    String address,
    @NotBlank
    String store,
    @NotNull
    Long minPrice,
    @NotNull
    Long deliveryCost,
    DeadlineEnum deadline,
    CategoryEnum category

    ) {

}
