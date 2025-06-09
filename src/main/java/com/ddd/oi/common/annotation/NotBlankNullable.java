package com.ddd.oi.common.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ddd.oi.common.annotation.handler.NotBlankNullableValidator;

@Constraint(validatedBy = NotBlankNullableValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankNullable {

	String message() default "공백일 수는 없습니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
