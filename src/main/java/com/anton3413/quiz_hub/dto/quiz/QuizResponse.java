package com.anton3413.quiz_hub.dto.quiz;

import java.util.UUID;

public record QuizResponse(
        UUID id,
        String title,
        String description,
        String username
) {
}
