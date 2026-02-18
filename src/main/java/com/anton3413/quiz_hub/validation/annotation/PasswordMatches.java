package com.anton3413.quiz_hub.validation.annotation;

import com.anton3413.quiz_hub.validation.validator.PasswordMatchesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatches {
    String message() default "The passwords don't match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}