package com.anton3413.quiz_hub.service.impl;

import com.anton3413.quiz_hub.dto.question.CreateQuestionRequest;
import com.anton3413.quiz_hub.dto.question.CreateQuestionsResponse;
import com.anton3413.quiz_hub.exception.QuizNotFoundException;
import com.anton3413.quiz_hub.mapper.QuestionMapper;
import com.anton3413.quiz_hub.model.Question;
import com.anton3413.quiz_hub.model.Quiz;
import com.anton3413.quiz_hub.repository.QuestionRepository;
import com.anton3413.quiz_hub.repository.QuizRepository;
import com.anton3413.quiz_hub.service.QuestionService;
import com.anton3413.quiz_hub.util.ApiMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Transactional
    public CreateQuestionsResponse addQuestionsToQuiz(UUID quizId, List<CreateQuestionRequest> requests, String username) {

        final Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(ApiMessages.ERROR_QUIZ_NOT_FOUND, quizId));

        if (!quiz.getAuthor().getUsername().equals(username)) {
            throw new AccessDeniedException(ApiMessages.ERROR_QUIZ_ACCESS_DENIED);
        }

        final List<Question> questions = questionMapper.toEntities(requests, quiz);

        questionRepository.saveAll(questions);
        questionRepository.flush();

        final int savedQuestionsToQuiz = questions.size();
        final int questionsInQuizNow = questionRepository.countByQuiz_Id(quizId);

        return new CreateQuestionsResponse(quizId, savedQuestionsToQuiz, questionsInQuizNow);
    }
}
