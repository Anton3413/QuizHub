package com.anton3413.quiz_hub.service.impl;

import com.anton3413.quiz_hub.dto.quiz.*;
import com.anton3413.quiz_hub.exception.QuizNotFoundException;
import com.anton3413.quiz_hub.mapper.QuizMapper;
import com.anton3413.quiz_hub.model.Quiz;
import com.anton3413.quiz_hub.model.User;
import com.anton3413.quiz_hub.repository.QuizRepository;
import com.anton3413.quiz_hub.service.QuizService;
import com.anton3413.quiz_hub.service.UserService;
import com.anton3413.quiz_hub.util.ApiMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final UserService userService;
    private final QuizMapper quizMapper;
    private final QuizRepository quizRepository;

    @Override
    @Transactional
    public QuizResponse createQuizForUser(CreateQuizRequest createQuizRequest, String username) {
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

    @Override
    public void deleteQuizById(UUID quizId, String username) {
       var quiz =  quizRepository.findById(quizId)
               .orElseThrow(() -> new QuizNotFoundException(ApiMessages.ERROR_QUIZ_NOT_FOUND, quizId));

       if(quiz.getAuthor().getUsername().equals(username)){
           quizRepository.delete(quiz);
       }
       else throw new  AccessDeniedException(ApiMessages.ERROR_QUIZ_ACCESS_DENIED);
    }

    @Override
    @Transactional
    public QuizResponse updateQuizById(UUID quizId, UpdateQuizRequest request, String username) {
        var quiz =  quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(ApiMessages.ERROR_QUIZ_NOT_FOUND, quizId));

        if(!quiz.getAuthor().getUsername().equals(username)){
            throw new  AccessDeniedException(ApiMessages.ERROR_QUIZ_ACCESS_DENIED);
        }
        else{
            if(request.isPublic()!=null && quiz.isPublic() != request.isPublic()){
                quiz.setPublic(request.isPublic());
            }
            if(request.title()!= null){
                quiz.setTitle(request.title());
            }
            if(request.description() != null){
                quiz.setDescription(request.description());
            }
        }
       Quiz updatedQuiz =  quizRepository.save(quiz);
       return quizMapper.fromEntityToCreateQuizResponse(updatedQuiz);
    }

    @Override
    public QuizDetailResponse findQuizById(UUID quizId, String username) {
        var quiz =  quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(ApiMessages.ERROR_QUIZ_NOT_FOUND, quizId));

        if(!quiz.getAuthor().getUsername().equals(username)){
            throw new  AccessDeniedException(ApiMessages.ERROR_QUIZ_ACCESS_DENIED);
        }
        return quizMapper.toDetailResponse(quiz);
    }
}
