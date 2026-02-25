package com.anton3413.quiz_hub.dto.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateQuizRequest(

        @NotBlank(message = "Title is required")
        @Size( min = 3, max = 150, message = "Title is too long")
        @JsonProperty("title")
        String title,

        @NotBlank(message = "Description is required")
        @Size(max = 1000, message = "Description is too long")
        @JsonProperty("description")
        String description,

        @JsonProperty("is_public")
        boolean isPublic
) {
}
