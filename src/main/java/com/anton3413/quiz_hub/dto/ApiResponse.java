package com.anton3413.quiz_hub.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public record ApiResponse(
        String message,
        LocalDateTime timestamp,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Map<String, String> errors
) {
    public ApiResponse(String message) {
        this(message, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), null);
    }
    public ApiResponse(String message, Map<String, String> errors) {
        this(message, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), errors);
    }
}
