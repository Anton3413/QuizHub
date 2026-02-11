package com.anton3413.quiz_hub.validation.validator;

import com.anton3413.quiz_hub.service.UserService;
import com.anton3413.quiz_hub.validation.annotation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.isEmailRegistered(email);
    }
}
