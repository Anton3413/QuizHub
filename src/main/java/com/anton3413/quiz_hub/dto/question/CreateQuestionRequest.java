package com.anton3413.quiz_hub.dto.question;

import com.anton3413.quiz_hub.dto.option.OptionRequest;
import com.anton3413.quiz_hub.model.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateQuestionRequest(
        @NotBlank
        String text,
        @NotNull
        QuestionType type,
        List<OptionRequest> options
) {
}
