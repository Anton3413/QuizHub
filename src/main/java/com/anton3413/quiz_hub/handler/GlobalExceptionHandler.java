package com.anton3413.quiz_hub.handler;

import com.anton3413.quiz_hub.dto.ApiResponse;
import com.anton3413.quiz_hub.exception.TokenExpiredException;
import com.anton3413.quiz_hub.exception.TokenNotFoundException;
import com.anton3413.quiz_hub.util.ApiMessages;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ApiResponse> handleNonExistentToken(TokenNotFoundException exception,
                                                              HttpServletRequest request){

        final String tokenValue = request.getParameter("token");

        log.warn("Account activation failed: Token [{}] not found.", tokenValue);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(exception.getMessage()));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ApiResponse> handleExpiredToken(TokenExpiredException exception,
                                                          HttpServletRequest request){

        final String tokenValue = request.getParameter("token");

        log.warn("Account activation failed: Token [{}] expired.", tokenValue);
        return ResponseEntity.status(HttpStatus.GONE).body(new ApiResponse(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationErrors(MethodArgumentNotValidException ex){

    final Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach(error -> {
        if (error instanceof FieldError fieldError) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        } else {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        }
    });

    log.warn("Validation failed: {} errors found", errors.size());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ApiResponse(ApiMessages.ERROR_VALIDATION_FAILED, errors));
    }
}
