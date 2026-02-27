package com.anton3413.quiz_hub.service;

import com.anton3413.quiz_hub.dto.quiz.*;

import java.util.List;
import java.util.UUID;

public interface QuizService {

    QuizResponse createQuizForUser(CreateQuizRequest createQuizRequest, String username);

    List<QuizSummaryResponse> findAllByUsername(String username);

    void deleteQuizById(UUID quizId, String username);

    QuizResponse updateQuizById(UUID quizId, UpdateQuizRequest request, String username);

    QuizDetailResponse findQuizById(UUID quizId, String username);
}
