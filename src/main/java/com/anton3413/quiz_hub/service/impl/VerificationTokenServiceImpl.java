package com.anton3413.quiz_hub.service.impl;

import com.anton3413.quiz_hub.config.SecurityConfig;
import com.anton3413.quiz_hub.model.User;
import com.anton3413.quiz_hub.model.VerificationToken;
import com.anton3413.quiz_hub.repository.VerificationTokenRepository;
import com.anton3413.quiz_hub.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final SecurityConfig securityConfig;


    @Override
    public VerificationToken generateForUser(User user) {
        VerificationToken token = VerificationToken.builder()
                .token(UUID.randomUUID())
                .user(user)
                .expiryDate(LocalDateTime.now().plusHours(securityConfig.getTokenExpiration()))
                .build();

        return verificationTokenRepository.save(token);
    }

    @Override
    public boolean verifyToken(String token) {
        return verificationTokenRepository.findVerificationTokenByToken(UUID.fromString(token))
                .map(maybeToken -> {
                    if (maybeToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                        verificationTokenRepository.delete(maybeToken);
                        return false;
                    }
                    User user = maybeToken.getUser();
                    user.setActivated(true);

                    verificationTokenRepository.delete(maybeToken);

                    return true;
                })
                .orElse(false);
    }
}
