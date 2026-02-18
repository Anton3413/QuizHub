package com.anton3413.quiz_hub.service.impl;

import com.anton3413.quiz_hub.dto.auth.CreateUserRequest;
import com.anton3413.quiz_hub.dto.auth.CreateUserResponse;
import com.anton3413.quiz_hub.event.UserRegisteredEvent;
import com.anton3413.quiz_hub.mapper.UserMapper;
import com.anton3413.quiz_hub.model.User;
import com.anton3413.quiz_hub.repository.UserRepository;
import com.anton3413.quiz_hub.service.UserService;
import com.anton3413.quiz_hub.util.ApiMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.isActivated(),
                        true,
                        true,
                        true,
                        AuthorityUtils.createAuthorityList("USER"))
                ).orElseThrow(() -> new UsernameNotFoundException(ApiMessages.ERROR_USER_NOT_FOUND));
    }

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
