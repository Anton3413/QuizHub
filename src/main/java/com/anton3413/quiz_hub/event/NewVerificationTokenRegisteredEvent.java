package com.anton3413.quiz_hub.event;

import com.anton3413.quiz_hub.model.User;

public record NewVerificationTokenRegisteredEvent(User user) {
}
