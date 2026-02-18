package com.anton3413.quiz_hub.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public record ApiResponse <T>(
        String message,
        Instant timestamp,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        T data,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Map<String, String> errors
) {
    public static <T> ApiResponse<T> of(String message) {
        return new ApiResponse<>(message, Instant.now().truncatedTo(ChronoUnit.SECONDS), null, null);
    }

    public static <T> ApiResponse<T> withData(String message, T data) {
        return new ApiResponse<>(message, Instant.now().truncatedTo(ChronoUnit.SECONDS), data, null);
    }

    public static <T> ApiResponse<T> withErrors(String message, Map<String, String> errors) {
        return new ApiResponse<>(message, Instant.now().truncatedTo(ChronoUnit.SECONDS), null, errors);
    }
}
