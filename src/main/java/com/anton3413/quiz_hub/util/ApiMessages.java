package com.anton3413.quiz_hub.util;

public final class ApiMessages {

    private ApiMessages(){}

    public static final String ERROR_ACTIVATION_TOKEN_INVALID = "Verification token is invalid or has already been used";
    public static final String ERROR_ACTIVATION_TOKEN_EXPIRED = "Activation token has expired. A new one has been sent to your registered email address";
    public static final String SUCCESS_USER_CREATED = "User has been created. An account activation email has been sent to the specified email address";
    public static final String SUCCESS_ACCOUNT_ACTIVATED = "Your account has been activated. You can now log in";
    public static final String ERROR_VALIDATION_FAILED = "Validation failed";
    public static final String ERROR_USER_NOT_FOUND = "There is no such user";
    public static final String ERROR_BAD_CREDENTIALS = "The login or password is incorrect";
    public static final String ERROR_ACCOUNT_NOT_ACTIVATED = "The account is not activated. Check your email to activate it";
    public static final String ERROR_JWT_TOKEN_INVALID = "Authorization error: token is invalid or missing";
    public static final String SUCCESS_LOGIN_SUCCESSFUL= "Login successful";
    public static final String ERROR_ACCOUNT_LOCKED = "Due to multiple failed login attempts, your account has been temporarily locked. Please try again later";
    public static final String ERROR_ACTIVATION_TOKEN_FORMAT_INVALID="This is an invalid activation token format. Please double-check the link";
    public static final String ERROR_ACTIVATION_TOKEN_ATTEMPTS_ENDED="The number of attempts to activate your account has expired. Contact the administration or register again using a new email";
    public static final String SUCCESS_QUIZ_CREATED = "New Quiz has  been successfully created";
    public static final String ERROR_QUIZ_NOT_FOUND = "Quiz test with the specified ID was not found. Please double-check that the ID is correct";
    public static final String ERROR_QUIZ_ACCESS_DENIED = "You are not the author of this quiz";
    public static final String SUCCESS_QUIZ_REMOVED = "Quiz has been deleted";
    public static final String SUCCESS_QUIZ_UPDATED = "Quiz has been updated";

}
