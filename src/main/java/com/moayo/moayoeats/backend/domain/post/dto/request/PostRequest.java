package com.moayo.moayoeats.backend.domain.post.dto.request;

import com.moayo.moayoeats.backend.domain.post.entity.CategoryEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostRequest(

    @NotBlank(message = "주소를 지정해 주세요")
    String address,
    @NotBlank(message = "가게명을 입력해 주세요")
    String store,
    @NotNull(message = "최소주문금액을 입력해 주세요")
    Integer minPrice,
    @NotNull(message = "배달비를 입력해 주세요")
    Integer deliveryCost,

    @NotNull(message = "마감시간 시를 입력해 주세요")
    @Max(value = 59,message = "분은 59보다 짧아야 합니다")
    Integer deadlineMins,
    @NotNull(message = "마감시간 분을 입력해 주세요")
    @Max(value = 12, message = "시간은 12시간보다 짧아야 합니다")
    Integer deadlineHours,
    CategoryEnum category

) {

}
