package com.moayo.moayoeats.backend.domain.post.exception.validator;


import com.moayo.moayoeats.backend.domain.post.entity.PostStatusEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PostStatusValidator implements ConstraintValidator<PostStatus, String> {

    @Override
    public void initialize(PostStatus status) {
    }

    @Override
    public boolean isValid(String status,
        ConstraintValidatorContext cxt) {
        return status.equals(PostStatusEnum.OPEN.toString())||
            status.equals(PostStatusEnum.CLOSED.toString())||
            status.equals(PostStatusEnum.ORDERED.toString())||
            status.equals(PostStatusEnum.RECEIVED.toString())||
            status.equals("ALL")
            ;
    }
}
