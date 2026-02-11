package com.anton3413.quiz_hub.validation.validator;

import com.anton3413.quiz_hub.service.UserService;
import com.anton3413.quiz_hub.validation.annotation.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.isUsernameRegistered(username);
    }
}
