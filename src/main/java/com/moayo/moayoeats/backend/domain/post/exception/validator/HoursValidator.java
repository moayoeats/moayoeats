package com.moayo.moayoeats.backend.domain.post.exception.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HoursValidator implements ConstraintValidator<Hours, String> {

    @Override
    public void initialize(Hours hours) {
    }

    @Override
    public boolean isValid(String hours, ConstraintValidatorContext cxt) {
        if (hours.equals("")) {
            return true;
        }
        return hours.matches("^[0-9]*$") && Integer.parseInt(hours) <= 12;
    }
}
