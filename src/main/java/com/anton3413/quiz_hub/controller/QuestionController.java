package com.anton3413.quiz_hub.controller;

import com.anton3413.quiz_hub.dto.ApiResponse;
import com.anton3413.quiz_hub.dto.question.CreateQuestionsBulkRequest;
import com.anton3413.quiz_hub.dto.question.CreateQuestionsResponse;
import com.anton3413.quiz_hub.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("quizzes/{quizId}/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    ResponseEntity<ApiResponse<CreateQuestionsResponse>> addQuestionsToQuiz(@PathVariable UUID quizId,
                                                                            @Valid @RequestBody
                                                                            CreateQuestionsBulkRequest bulkRequest,
                                                                            Principal principal){

      final var response =  questionService.addQuestionsToQuiz(quizId, bulkRequest.questions(), principal.getName());

       return ResponseEntity.status(HttpStatus.CREATED)
               .body(ApiResponse.withData("Success", response));
    }
}
