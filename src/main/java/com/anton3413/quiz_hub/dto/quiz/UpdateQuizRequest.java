package com.anton3413.quiz_hub.dto.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

public record UpdateQuizRequest(

        @Size(min = 3, max = 150, message = "Title is too long")
                String title,

        @Size(max = 1000, message = "Description is too long")
        String description,

        @JsonProperty("is_public")
        Boolean isPublic
) {
}
