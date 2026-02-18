package com.anton3413.quiz_hub.exception;

public class ActivationTokenNotFoundException extends RuntimeException {
    public ActivationTokenNotFoundException(String message) {
        super(message);
    }
}
