package com.anton3413.quiz_hub.dto.option;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record OptionRequest(
        @NotBlank
        String text,

        @JsonProperty("is_correct")
        boolean isCorrect
) {
}
