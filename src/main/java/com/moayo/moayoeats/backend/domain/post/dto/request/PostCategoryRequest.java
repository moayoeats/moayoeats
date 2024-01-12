package com.moayo.moayoeats.backend.domain.post.dto.request;

import com.moayo.moayoeats.backend.domain.post.exception.validator.Category;
import jakarta.validation.constraints.NotNull;

public record PostCategoryRequest(@NotNull @Category String category) {

}
