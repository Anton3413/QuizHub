package com.anton3413.quiz_hub.dto.question;

import java.util.List;

public record CreateQuestionsBulkRequest(
        List<CreateQuestionRequest> questions
) {
}
