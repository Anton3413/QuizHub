package com.anton3413.quiz_hub.service;

import com.anton3413.quiz_hub.dto.auth.CreateUserRequest;
import com.anton3413.quiz_hub.dto.auth.CreateUserResponse;
import com.anton3413.quiz_hub.model.User;

import java.util.Optional;

public interface UserService {

    boolean isUsernameRegistered(String username);

    boolean isEmailRegistered(String email);

    CreateUserResponse register(CreateUserRequest createUserRequest);

    User findByUsername(String username);

    public void incrementFailedAttempts(String username);

    public void resetFailedAttempts(String username);
}
