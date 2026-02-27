package com.anton3413.quiz_hub.controller;

import com.anton3413.quiz_hub.dto.ApiResponse;
import com.anton3413.quiz_hub.dto.quiz.*;
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
    public ResponseEntity<ApiResponse<QuizResponse>> createNewQuiz(@RequestBody @Valid CreateQuizRequest createQuizRequest,
                                                                   Principal principal){
        final String username =  principal.getName();

        QuizResponse response = quizService.createQuizForUser(createQuizRequest, username);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.withData(ApiMessages.SUCCESS_QUIZ_CREATED,response));
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<ApiResponse<QuizDetailResponse>> getDetailedQuizById(@PathVariable UUID quizId,
                                                                               Principal principal){
        final String username = principal.getName();
        final var response = quizService.findQuizById(quizId, username);

        return ResponseEntity.ok(ApiResponse.withData("", response));
    }
    @GetMapping()
    public ResponseEntity<ApiResponse<List<QuizSummaryResponse>>> getOwnQuizzes(Principal principal){

        final String username = principal.getName();

        final List<QuizSummaryResponse> quizzes = quizService.findAllByUsername(username);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.withData("My quizzes", quizzes));
    }

    @PatchMapping("/{quizId}")
    public ResponseEntity<ApiResponse<QuizResponse>> updateQuiz(@RequestBody @Valid UpdateQuizRequest updateQuizRequest,
                                                               @PathVariable UUID quizId,
                                                               Principal principal){
        final String username =  principal.getName();

        var response =  quizService.updateQuizById(quizId,updateQuizRequest,username);

       return ResponseEntity.ok()
               .body(ApiResponse.withData(ApiMessages.SUCCESS_QUIZ_UPDATED, response));
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<ApiResponse> removeQuiz(@PathVariable UUID quizId, Principal principal){
        quizService.deleteQuizById(quizId, principal.getName());

        return ResponseEntity.ok(ApiResponse.of(ApiMessages.SUCCESS_QUIZ_REMOVED));
    }
}
