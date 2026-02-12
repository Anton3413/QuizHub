package com.anton3413.quiz_hub.service.impl;

import com.anton3413.quiz_hub.model.VerificationToken;
import com.anton3413.quiz_hub.repository.VerificationTokenRepository;
import com.anton3413.quiz_hub.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;


    @Override
    public VerificationToken save(VerificationToken token) {
        return verificationTokenRepository.save(token);
    }
}
