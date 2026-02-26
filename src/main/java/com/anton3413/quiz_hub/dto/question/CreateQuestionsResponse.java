package com.anton3413.quiz_hub.dto.question;

import java.util.UUID;

public record CreateQuestionsResponse(
        UUID quizId,
        int questionsAdded,
        int totalQuestionsNow
) {
}
