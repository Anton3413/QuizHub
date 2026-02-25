package com.anton3413.quiz_hub.service;

import com.anton3413.quiz_hub.dto.auth.CreateUserRequest;
import com.anton3413.quiz_hub.dto.auth.CreateUserResponse;
import com.anton3413.quiz_hub.model.User;

public interface UserService {

    boolean isUsernameRegistered(String username);

    boolean isEmailRegistered(String email);

    CreateUserResponse register(CreateUserRequest createUserRequest);

    User findByUsername(String username);

    void incrementFailedAttempts(String username);

    void resetFailedAttempts(String username);
}
