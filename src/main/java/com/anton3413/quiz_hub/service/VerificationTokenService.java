package com.anton3413.quiz_hub.service;

import com.anton3413.quiz_hub.model.User;
import com.anton3413.quiz_hub.model.VerificationToken;

public interface VerificationTokenService {

    VerificationToken generateForUser(User user);

    void verifyToken(String token);
}
