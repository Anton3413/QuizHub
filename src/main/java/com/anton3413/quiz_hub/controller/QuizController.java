package com.anton3413.quiz_hub.controller;

import com.anton3413.quiz_hub.dto.ApiResponse;
import com.anton3413.quiz_hub.dto.quiz.CreateQuizRequest;
import com.anton3413.quiz_hub.dto.quiz.CreateQuizResponse;
import com.anton3413.quiz_hub.service.QuizService;
import com.anton3413.quiz_hub.util.ApiMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController()
@RequestMapping("/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateQuizResponse>> createNewQuiz(@RequestBody @Valid CreateQuizRequest createQuizRequest,
                                                                         Principal principal){
        final String username =  principal.getName();

        CreateQuizResponse response = quizService.createQuizForUser(createQuizRequest, username);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.withData(ApiMessages.SUCCESS_TEST_CREATED,response));
    }
}
