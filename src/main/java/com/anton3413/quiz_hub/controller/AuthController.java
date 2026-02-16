package com.anton3413.quiz_hub.controller;

import com.anton3413.quiz_hub.dto.ApiResponse;
import com.anton3413.quiz_hub.dto.user.CreateUserRequest;
import com.anton3413.quiz_hub.dto.user.CreateUserResponse;
import com.anton3413.quiz_hub.service.UserService;
import com.anton3413.quiz_hub.service.VerificationTokenService;
import com.anton3413.quiz_hub.util.ApiMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final  VerificationTokenService tokenService;
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> createNewAccount(@RequestBody @Valid CreateUserRequest createUserRequest){
        final CreateUserResponse response =  userService.register(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/confirm")
    public ResponseEntity<ApiResponse> activateAccountWithToken(@RequestParam String token){
        tokenService.verifyToken(token);

        return ResponseEntity.ok(new ApiResponse(ApiMessages.SUCCESS_TOKEN_ACTIVATED));
    }
}