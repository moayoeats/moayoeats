package com.moayo.moayoeats.domain.post.dto.request;

import com.moayo.moayoeats.domain.post.exception.validator.Category;
import com.moayo.moayoeats.domain.post.entity.CategoryEnum;

public record PostCategoryRequest (
    @Category
    String category
){
}
