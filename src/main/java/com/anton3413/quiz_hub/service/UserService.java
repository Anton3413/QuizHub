package com.anton3413.quiz_hub.service;

import com.anton3413.quiz_hub.dto.user.CreateUserRequest;
import com.anton3413.quiz_hub.dto.user.CreateUserResponse;

public interface UserService {

    boolean isUsernameRegistered(String username);

    boolean isEmailRegistered(String email);

    CreateUserResponse register(CreateUserRequest createUserRequest);
}
