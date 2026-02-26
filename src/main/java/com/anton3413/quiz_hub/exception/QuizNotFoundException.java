package com.anton3413.quiz_hub.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

import java.util.UUID;

@Getter
public class QuizNotFoundException extends EntityNotFoundException {

    private final UUID quizId;

    public QuizNotFoundException(String message, UUID quizId) {
        super(message);
        this.quizId = quizId;
    }
}
