package com.anton3413.quiz_hub.service.impl;

import com.anton3413.quiz_hub.event.NewVerificationTokenRegisteredEvent;
import com.anton3413.quiz_hub.exception.AccountActivationAttemptsHaveEnded;
import com.anton3413.quiz_hub.util.ApiMessages;
import com.anton3413.quiz_hub.exception.ActivationTokenExpiredException;
import com.anton3413.quiz_hub.exception.ActivationTokenNotFoundException;
import com.anton3413.quiz_hub.model.User;
import com.anton3413.quiz_hub.model.VerificationToken;
import com.anton3413.quiz_hub.repository.VerificationTokenRepository;
import com.anton3413.quiz_hub.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${application.security.activation.activation-attempts}")
    private int activationAttempts;

    @Value("${application.security.activation.token-expiration}")
    private int tokenExpiration;


    @Transactional
    @Override
    public VerificationToken generateForUser(User user) {
        VerificationToken token = VerificationToken.builder()
                .token(UUID.randomUUID())
                .user(user)
                .verificationAttempts(1)
                .expiryDate(Instant.now().plus(tokenExpiration, ChronoUnit.HOURS))
                .build();

        return verificationTokenRepository.save(token);
    }

    @Transactional(noRollbackFor = {ActivationTokenExpiredException.class})
    @Override
    public void verifyToken(String token) {
        UUID uuid = parseFromString(token);

        verificationTokenRepository.findVerificationTokenByToken(uuid)
                .map(maybeToken -> {
                    if (isExpired(maybeToken)) {

                        if(maybeToken.getVerificationAttempts() <= activationAttempts){
                            generateNewTokenWhenPreviousExpired(maybeToken);
                            eventPublisher.publishEvent(new NewVerificationTokenRegisteredEvent(maybeToken.getUser()));
                            throw new ActivationTokenExpiredException(ApiMessages.ERROR_ACTIVATION_TOKEN_EXPIRED);
                        }
                        else{
                            throw new AccountActivationAttemptsHaveEnded(ApiMessages.ERROR_ACTIVATION_TOKEN_ATTEMPTS_ENDED);
                        }
                    }
                    else {
                        User user = maybeToken.getUser();
                        user.setVerificationToken(null);
                        user.setActivated(true);
                        return true;
                    }
                })
                .orElseThrow(() -> new ActivationTokenNotFoundException(ApiMessages.ERROR_ACTIVATION_TOKEN_INVALID));
    }


    private void generateNewTokenWhenPreviousExpired(VerificationToken verificationToken){
        verificationToken.setToken(UUID.randomUUID());
        verificationToken.setVerificationAttempts(verificationToken.getVerificationAttempts() + 1);
    }

    private UUID parseFromString(String token){
        UUID uuid;
        try {
            uuid = UUID.fromString(token);
        } catch (IllegalArgumentException e) {
            throw new ActivationTokenNotFoundException(ApiMessages.ERROR_ACTIVATION_TOKEN_FORMAT_INVALID);
        }
        return uuid;
    }

    private boolean isExpired(VerificationToken verificationToken){
      return verificationToken.getExpiryDate().isBefore(Instant.now());
    }
}
