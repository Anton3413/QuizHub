package com.anton3413.quiz_hub.exception;

public class AccountActivationAttemptsHaveEnded extends RuntimeException {
    public AccountActivationAttemptsHaveEnded(String message) {
        super(message);
    }
}
