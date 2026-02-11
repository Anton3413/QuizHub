package com.anton3413.quiz_hub.dto.user;

import java.util.UUID;

public record CreateUserResponse(
        String message,
        String username,
        UUID userId) {
}
