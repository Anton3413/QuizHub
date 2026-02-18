package com.anton3413.quiz_hub.service.impl;

import com.anton3413.quiz_hub.security.SecurityConfig;
import com.anton3413.quiz_hub.util.ApiMessages;
import com.anton3413.quiz_hub.exception.ActivationTokenExpiredException;
import com.anton3413.quiz_hub.exception.ActivationTokenNotFoundException;
import com.anton3413.quiz_hub.model.User;
import com.anton3413.quiz_hub.model.VerificationToken;
import com.anton3413.quiz_hub.repository.VerificationTokenRepository;
import com.anton3413.quiz_hub.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final SecurityConfig securityConfig;


    @Transactional
    @Override
    public VerificationToken generateForUser(User user) {
        VerificationToken token = VerificationToken.builder()
                .token(UUID.randomUUID())
                .user(user)
                .expiryDate(LocalDateTime.now().plusHours(securityConfig.getTokenExpiration()))
                .build();

        return verificationTokenRepository.save(token);
    }

    @Transactional
    @Override
    public void verifyToken(String token) {
        UUID uuid;
        try {
            uuid = UUID.fromString(token);
        } catch (IllegalArgumentException e) {
            throw new ActivationTokenNotFoundException(ApiMessages.ERROR_ACTIVATION_TOKEN_INVALID);
        }

        verificationTokenRepository.findVerificationTokenByToken(uuid)
                .map(maybeToken -> {
                    if (maybeToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                        throw new ActivationTokenExpiredException(ApiMessages.ERROR_ACTIVATION_TOKEN_EXPIRED);
                    }
                    User user = maybeToken.getUser();
                    user.setVerificationToken(null);

                    return true;
                })
                .orElseThrow(() -> new ActivationTokenNotFoundException(ApiMessages.ERROR_ACTIVATION_TOKEN_INVALID));
    }
}
