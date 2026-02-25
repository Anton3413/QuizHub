package com.anton3413.quiz_hub.dto.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.UUID;

public record QuizSummaryResponse(
        UUID id,
        String title,

        @JsonProperty("is_public")
        boolean isPublic,

        @JsonProperty("created_at")
        Instant createdAt
) {
}
