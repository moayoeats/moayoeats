package com.moayo.moayoeats.backend.domain.post.dto.request;

import jakarta.validation.constraints.NotNull;

public record PostSearchRequest(
    @NotNull(message = "검색어를 입력하세요.")
    String keyword
) {

}
