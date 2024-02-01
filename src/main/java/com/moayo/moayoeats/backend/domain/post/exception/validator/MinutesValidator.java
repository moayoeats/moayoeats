package com.moayo.moayoeats.backend.domain.post.exception.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MinutesValidator implements ConstraintValidator<Minutes, String> {

    @Override
    public void initialize(Minutes minutes) {
    }

    @Override
    public boolean isValid(String mins, ConstraintValidatorContext cxt) {
        if (mins.equals("")) {
            return true;
        }
        return mins.matches("^[0-9]*$") && Integer.parseInt(mins) <= 59;
    }
}
