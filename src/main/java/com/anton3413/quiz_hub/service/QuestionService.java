package com.anton3413.quiz_hub.service;

import com.anton3413.quiz_hub.dto.question.CreateQuestionRequest;
import com.anton3413.quiz_hub.dto.question.CreateQuestionsResponse;

import java.util.List;
import java.util.UUID;

public interface QuestionService {

    CreateQuestionsResponse addQuestionsToQuiz(UUID quizId, List<CreateQuestionRequest> requests, String username);
}
