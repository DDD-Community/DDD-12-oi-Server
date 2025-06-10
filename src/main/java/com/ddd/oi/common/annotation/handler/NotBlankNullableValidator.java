package com.ddd.oi.common.annotation.handler;

import com.ddd.oi.common.annotation.NotBlankNullable;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankNullableValidator implements ConstraintValidator<NotBlankNullable, String> {

    @Override
    public void initialize(NotBlankNullable constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return !value.trim().isEmpty();
    }
}
