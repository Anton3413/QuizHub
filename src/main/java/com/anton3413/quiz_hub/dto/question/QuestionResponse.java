package com.anton3413.quiz_hub.dto.question;

import com.anton3413.quiz_hub.dto.option.OptionResponse;

import java.util.List;

public record QuestionResponse(
        Long id,
        String text,
        String type,
        List<OptionResponse> options
) {
}
