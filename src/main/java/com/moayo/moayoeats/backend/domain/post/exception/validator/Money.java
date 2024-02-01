package com.moayo.moayoeats.backend.domain.post.exception.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = MoneyValidator.class)
@Target( {ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Money {
    String message() default "금액을 숫자로만 입력해 주세요!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
