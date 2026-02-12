package com.anton3413.quiz_hub.event.listener;

import com.anton3413.quiz_hub.config.SecurityConfig;
import com.anton3413.quiz_hub.event.UserRegisteredEvent;
import com.anton3413.quiz_hub.model.User;
import com.anton3413.quiz_hub.model.VerificationToken;
import com.anton3413.quiz_hub.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GlobalEventListener {

    private final SecurityConfig securityConfig;
    private final VerificationTokenService verificationTokenService;

    @EventListener
    public void onUserRegistration(UserRegisteredEvent event) {
        User user = event.user();

        VerificationToken token = VerificationToken.builder()
                .token(UUID.randomUUID())
                .user(user)
                .expiryDate(LocalDateTime.now().plusHours(securityConfig.getToken_expiration()))
                .build();

        verificationTokenService.save(token);

        System.out.println("Логика регистрации для: " + user.getUsername());

    }

}