package com.anton3413.quiz_hub.util;

public final class ApiMessages {

    private ApiMessages(){}

    public static final String ERROR_ACTIVATION_TOKEN_INVALID = "Verification link is invalid or has already been used";
    public static final String ERROR_ACTIVATION_TOKEN_EXPIRED = "Activation link has expired. Please request a new one";
    public static final String SUCCESS_USER_CREATED = "User has been created";
    public static final String SUCCESS_ACCOUNT_ACTIVATED = "Your account has been activated";
    public static final String ERROR_VALIDATION_FAILED = "Validation failed";
    public static final String ERROR_USER_NOT_FOUND = "There is no such user";
    public static final String ERROR_BAD_CREDENTIALS = "The login or password is incorrect";
    public static final String ERROR_ACCOUNT_NOT_ACTIVATED = "The account is not activated. Check your email or request a new token.";
    public static final String ERROR_JWT_TOKEN_INVALID = "Authorization error: token is invalid or missing";
}
