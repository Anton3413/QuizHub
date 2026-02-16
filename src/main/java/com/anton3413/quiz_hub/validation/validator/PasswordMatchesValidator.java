package com.anton3413.quiz_hub.validation.validator;

import com.anton3413.quiz_hub.dto.auth.CreateUserRequest;
import com.anton3413.quiz_hub.validation.annotation.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, CreateUserRequest> {

    @Override
    public boolean isValid(CreateUserRequest createUserRequest, ConstraintValidatorContext constraintValidatorContext) {
        return createUserRequest.password().equals(createUserRequest.confirmPassword());
    }
}
