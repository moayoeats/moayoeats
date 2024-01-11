package com.moayo.moayoeats.domain.post.dto.request;

import com.moayo.moayoeats.domain.post.exception.validator.Enum;
import com.moayo.moayoeats.domain.post.entity.CategoryEnum;

public record PostCategoryRequest(
    CategoryEnum category
){
}
