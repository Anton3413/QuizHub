package com.anton3413.quiz_hub.controller;

import com.anton3413.quiz_hub.dto.ApiResponse;
import com.anton3413.quiz_hub.dto.quiz.CreateQuizRequest;
import com.anton3413.quiz_hub.dto.quiz.CreateQuizResponse;
import com.anton3413.quiz_hub.dto.quiz.QuizSummaryResponse;
import com.anton3413.quiz_hub.service.QuizService;
import com.anton3413.quiz_hub.util.ApiMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

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
                .body(ApiResponse.withData(ApiMessages.SUCCESS_QUIZ_CREATED,response));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<QuizSummaryResponse>>> getOwnQuizzes(Principal principal){

        final String username = principal.getName();

        final List<QuizSummaryResponse> quizzes = quizService.findAllByUsername(username);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.withData("My quizzes", quizzes));
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<ApiResponse> removeQuiz(@PathVariable UUID quizId, Principal principal){
        quizService.deleteQuizById(quizId, principal.getName());

        return ResponseEntity.ok(ApiResponse.of(ApiMessages.SUCCESS_QUIZ_REMOVED));
    }
}
