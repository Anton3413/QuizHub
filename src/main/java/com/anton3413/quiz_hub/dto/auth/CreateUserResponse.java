package com.anton3413.quiz_hub.dto.auth;

import java.util.UUID;

public record CreateUserResponse(
        String username,
        UUID userId,
        String email
) {}
