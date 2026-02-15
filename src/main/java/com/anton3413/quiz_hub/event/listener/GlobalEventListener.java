package com.anton3413.quiz_hub.event.listener;

import com.anton3413.quiz_hub.config.SecurityConfig;
import com.anton3413.quiz_hub.event.UserRegisteredEvent;
import com.anton3413.quiz_hub.model.User;
import com.anton3413.quiz_hub.model.VerificationToken;
import com.anton3413.quiz_hub.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class GlobalEventListener {

    private final SecurityConfig securityConfig;
    private final VerificationTokenService verificationTokenService;

    @EventListener
    public void onUserRegistration(UserRegisteredEvent event) {
        User user = event.user();

        VerificationToken token = verificationTokenService.generateForUser(user);

        log.info("Follow this link to activate account for {}: {}/auth/confirm?token={}",
                user.getUsername(),
                securityConfig.getBaseUrl(),
                token.getToken());
    }
}