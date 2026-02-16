package com.anton3413.quiz_hub.service.impl;

import com.anton3413.quiz_hub.dto.user.CreateUserRequest;
import com.anton3413.quiz_hub.dto.user.CreateUserResponse;
import com.anton3413.quiz_hub.event.UserRegisteredEvent;
import com.anton3413.quiz_hub.mapper.UserMapper;
import com.anton3413.quiz_hub.model.User;
import com.anton3413.quiz_hub.repository.UserRepository;
import com.anton3413.quiz_hub.service.UserService;
import com.anton3413.quiz_hub.util.ApiMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public CreateUserResponse register(CreateUserRequest createUserRequest) {

        User user = userMapper.fromCreateUserRequestToEntity(createUserRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        eventPublisher.publishEvent(new UserRegisteredEvent(savedUser));

        return userMapper.fromEntityToCreateUserResponse(savedUser, ApiMessages.SUCCESS_USER_CREATED);
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
