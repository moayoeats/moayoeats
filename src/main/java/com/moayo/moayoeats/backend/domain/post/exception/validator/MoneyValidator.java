package com.moayo.moayoeats.backend.domain.post.exception.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MoneyValidator implements ConstraintValidator<Money, String> {

    @Override
    public void initialize(Money money) {
    }

    @Override
    public boolean isValid(String money, ConstraintValidatorContext cxt) {
        if (money.equals("")) {
            return false;
        }
        return money.matches("^[0-9]*$");
    }
}
