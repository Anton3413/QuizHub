package com.anton3413.quiz_hub.handler;

import com.anton3413.quiz_hub.dto.ApiResponse;
import com.anton3413.quiz_hub.exception.ActivationTokenExpiredException;
import com.anton3413.quiz_hub.exception.ActivationTokenNotFoundException;
import com.anton3413.quiz_hub.util.ApiMessages;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ActivationTokenNotFoundException.class)
    public ResponseEntity<ApiResponse> handleNonExistentActivationToken(ActivationTokenNotFoundException exception,
                                                              HttpServletRequest request){

        final String tokenValue = request.getParameter("token");

        log.warn("Account activation failed: Token [{}] not found.", tokenValue);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.of(exception.getMessage()));
    }

    @ExceptionHandler(ActivationTokenExpiredException.class)
    public ResponseEntity<ApiResponse> handleExpiredActivationToken(ActivationTokenExpiredException exception,
                                                                    HttpServletRequest request){

        final String tokenValue = request.getParameter("token");

        log.warn("Account activation failed: Token [{}] expired.", tokenValue);
        return ResponseEntity.status(HttpStatus.GONE).body(ApiResponse.of(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationErrors(MethodArgumentNotValidException ex){

    final Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach(error -> {
        if (error instanceof FieldError fieldError) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        } else {
            errors.put("global_error", error.getDefaultMessage());
        }
    });

    log.warn("Validation failed: {} errors found", errors.size());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.withErrors(ApiMessages.ERROR_VALIDATION_FAILED, errors));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentials(BadCredentialsException e) {
        log.warn("Authorization failed: login or password is incorrect");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.of(ApiMessages.ERROR_BAD_CREDENTIALS));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResponse> handleDisabled(DisabledException e) {
        log.warn("Authorization failed: Account not activated");

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.of(ApiMessages.ERROR_ACCOUNT_NOT_ACTIVATED));
    }


    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiResponse> handleAuthException(Exception e) {

        log.warn("Authorization error: JWT token is invalid or expired");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.of(ApiMessages.ERROR_JWT_TOKEN_INVALID));
    }


}
