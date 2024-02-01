package com.moayo.moayoeats.backend.domain.post.exception.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PostStatusValidator.class)
@Target( {ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PostStatus {
    String message() default "has to be one of the PostStatusEnum";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
