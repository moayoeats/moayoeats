package com.moayo.moayoeats.backend.domain.post.dto.request;

import com.moayo.moayoeats.backend.domain.post.entity.CategoryEnum;
import com.moayo.moayoeats.backend.domain.post.exception.validator.Category;
import com.moayo.moayoeats.backend.domain.post.exception.validator.Hours;
import com.moayo.moayoeats.backend.domain.post.exception.validator.Minutes;
import com.moayo.moayoeats.backend.domain.post.exception.validator.Money;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostRequest(

    @NotBlank(message = "주소를 지정해 주세요")
    String address,
    @NotBlank(message = "가게명을 입력해 주세요")
    String store,
    @Money
    String minPrice,
    @Money
    String deliveryCost,

    @Minutes
    String deadlineMins,

    @Hours
    String deadlineHours,

    @Category
    String category

) {

}
