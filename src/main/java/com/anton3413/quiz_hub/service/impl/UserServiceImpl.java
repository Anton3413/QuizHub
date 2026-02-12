package com.anton3413.quiz_hub.service.impl;

import com.anton3413.quiz_hub.dto.user.CreateUserRequest;
import com.anton3413.quiz_hub.dto.user.CreateUserResponse;
import com.anton3413.quiz_hub.event.UserRegisteredEvent;
import com.anton3413.quiz_hub.mapper.UserMapper;
import com.anton3413.quiz_hub.model.User;
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
    private final UserMapper userMapper;


    @Transactional
    public CreateUserResponse register(CreateUserRequest request) {
       User user =  userRepository.save(userMapper.fromCreateUserRequestToEntity(request));

       eventPublisher.publishEvent(new UserRegisteredEvent(user));

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
