package com.moayo.moayoeats.backend.domain.post.exception.validator;

import com.moayo.moayoeats.backend.domain.post.entity.CategoryEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<Category, String> {

    @Override
    public void initialize(Category category) {
    }

    @Override
    public boolean isValid(String categoryEnum,
        ConstraintValidatorContext cxt) {
        return categoryEnum.equals(CategoryEnum.ALL.toString())
            ||categoryEnum.equals(CategoryEnum.ASIAN.toString())
            ||categoryEnum.equals(CategoryEnum.BURGER.toString())
            ||categoryEnum.equals(CategoryEnum.CHICKEN.toString())
            ||categoryEnum.equals(CategoryEnum.CHINESE.toString())
            ||categoryEnum.equals(CategoryEnum.JAPANESE.toString())
            ||categoryEnum.equals(CategoryEnum.KOREAN.toString())
            ||categoryEnum.equals(CategoryEnum.LUNCHBOX.toString())
            ||categoryEnum.equals(CategoryEnum.PIZZA.toString())
            ||categoryEnum.equals(CategoryEnum.SNACK.toString())
            ||categoryEnum.equals(CategoryEnum.WESTERN.toString())
            ||categoryEnum.equals("CAFE")
            ;
    }

}
