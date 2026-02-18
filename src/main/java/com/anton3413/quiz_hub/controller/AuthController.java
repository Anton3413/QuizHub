package com.anton3413.quiz_hub.controller;

import com.anton3413.quiz_hub.dto.ApiResponse;
import com.anton3413.quiz_hub.dto.auth.CreateUserRequest;
import com.anton3413.quiz_hub.dto.auth.CreateUserResponse;
import com.anton3413.quiz_hub.dto.auth.LoginRequest;
import com.anton3413.quiz_hub.dto.auth.LoginResponse;
import com.anton3413.quiz_hub.security.JwtService;
import com.anton3413.quiz_hub.service.UserService;
import com.anton3413.quiz_hub.service.VerificationTokenService;
import com.anton3413.quiz_hub.util.ApiMessages;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final VerificationTokenService tokenService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CreateUserResponse>> createNewAccount(@RequestBody @Valid CreateUserRequest createUserRequest){
        final CreateUserResponse response =  userService.register(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.withData(ApiMessages.SUCCESS_USER_CREATED, response));
    }

    @GetMapping("/activate")
    public ResponseEntity<ApiResponse> activateAccountWithToken(@RequestParam String token){
        tokenService.verifyToken(token);

        return ResponseEntity.ok(ApiResponse.of(ApiMessages.SUCCESS_ACCOUNT_ACTIVATED));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {

        final Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        final UserDetails userDetails = (UserDetails) auth.getPrincipal();

        final String jwtToken = jwtService.generateToken(userDetails);
        final Instant expiresAt = jwtService.extractClaim(jwtToken, Claims::getExpiration).toInstant();

        final LoginResponse response = new LoginResponse(jwtToken, "Bearer", expiresAt, userDetails.getUsername());

        return ResponseEntity.ok(ApiResponse.withData(ApiMessages.SUCCESS_LOGIN_SUCCESSFUL, response));
    }
}