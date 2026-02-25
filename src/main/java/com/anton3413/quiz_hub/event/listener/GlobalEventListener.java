package com.anton3413.quiz_hub.event.listener;

import com.anton3413.quiz_hub.event.NewVerificationTokenRegisteredEvent;
import com.anton3413.quiz_hub.security.SecurityConfig;
import com.anton3413.quiz_hub.event.UserRegisteredEvent;
import com.anton3413.quiz_hub.model.User;
import com.anton3413.quiz_hub.model.VerificationToken;
import com.anton3413.quiz_hub.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class GlobalEventListener {

    private final SecurityConfig securityConfig;
    private final VerificationTokenService verificationTokenService;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @EventListener
    public void onUserRegistration(UserRegisteredEvent event) {
        User user = event.user();

        VerificationToken token = verificationTokenService.generateForUser(user);

        String activationLink = String.format("%s%s/auth/activate?token=%s",
                securityConfig.getBaseUrl(),
                contextPath,
                token.getToken());

        log.info("Activation link for {}: {}", user.getUsername(), activationLink);
    }

    @EventListener
    public void onNewTokenGeneration(NewVerificationTokenRegisteredEvent event){
        var user = event.user();
        var token = event.user().getVerificationToken();

        String activationLink = String.format("%s%s/auth/activate?token=%s",
                securityConfig.getBaseUrl(),
                contextPath,
                token.getToken());

        log.info("New activation link generated for {}: {}", user.getUsername(), activationLink);
    }
}