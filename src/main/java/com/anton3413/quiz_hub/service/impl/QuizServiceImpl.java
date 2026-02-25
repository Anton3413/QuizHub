package com.anton3413.quiz_hub.service.impl;

import com.anton3413.quiz_hub.dto.quiz.CreateQuizRequest;
import com.anton3413.quiz_hub.dto.quiz.CreateQuizResponse;
import com.anton3413.quiz_hub.dto.quiz.QuizSummaryResponse;
import com.anton3413.quiz_hub.mapper.QuizMapper;
import com.anton3413.quiz_hub.model.Quiz;
import com.anton3413.quiz_hub.model.User;
import com.anton3413.quiz_hub.repository.QuizRepository;
import com.anton3413.quiz_hub.service.QuizService;
import com.anton3413.quiz_hub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final UserService userService;
    private final QuizMapper quizMapper;
    private final QuizRepository quizRepository;

    @Override
    @Transactional
    public CreateQuizResponse createQuizForUser(CreateQuizRequest createQuizRequest, String username) {
        final User user = userService.findByUsername(username);

        Quiz quiz = quizMapper.fromCreateQuizRequestToEntity(createQuizRequest);
        quiz.setAuthor(user);

        final Quiz savedQuiz = quizRepository.save(quiz);

        return quizMapper.fromEntityToCreateQuizResponse(savedQuiz);
    }

    @Override
    public List<QuizSummaryResponse> findAllByUsername(String username) {
        return quizRepository.findAllByAuthor_Username(username)
                .stream()
                .map(quizMapper::fromEntityToQuizSummaryResponse)
                .toList();
    }
}
