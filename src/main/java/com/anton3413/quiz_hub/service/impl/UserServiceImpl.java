package com.anton3413.quiz_hub.service.impl;

import com.anton3413.quiz_hub.dto.user.CreateUserRequest;
import com.anton3413.quiz_hub.dto.user.CreateUserResponse;
import com.anton3413.quiz_hub.repository.UserRepository;
import com.anton3413.quiz_hub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    public CreateUserResponse register(CreateUserRequest request) {
     /*   User user =
                userRepository.save(user);

        eventPublisher.publishEvent(new UserRegisteredEvent(user));*/
        return null;
    }

    @Override
    public boolean isUsernameRegistered(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean isEmailRegistered(String email) {
        return userRepository.existsByEmail(email);
    }
}
