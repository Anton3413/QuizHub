package com.anton3413.quiz_hub.dto.quiz;

import com.anton3413.quiz_hub.dto.question.QuestionResponse;

import java.util.List;
import java.util.UUID;

public record QuizDetailResponse(
        UUID id,
        String title,
        String description,
        String authorName,
        List<QuestionResponse> questions
) {
}
