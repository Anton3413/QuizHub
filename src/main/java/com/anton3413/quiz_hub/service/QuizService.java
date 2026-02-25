package com.anton3413.quiz_hub.service;

import com.anton3413.quiz_hub.dto.quiz.CreateQuizRequest;
import com.anton3413.quiz_hub.dto.quiz.CreateQuizResponse;
import com.anton3413.quiz_hub.dto.quiz.QuizSummaryResponse;

import java.util.List;

public interface QuizService {

    CreateQuizResponse createQuizForUser(CreateQuizRequest createQuizRequest, String username);

    List<QuizSummaryResponse> findAllByUsername(String username);
}
