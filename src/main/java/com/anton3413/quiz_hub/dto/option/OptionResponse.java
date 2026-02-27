package com.anton3413.quiz_hub.dto.option;


public record OptionResponse(
        Long id,
        String text,
        boolean isCorrect
) {
}
