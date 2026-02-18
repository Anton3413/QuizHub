package com.anton3413.quiz_hub.dto.auth;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record LoginResponse(

        @JsonProperty("access_token")
        String access_token,

        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("expires_at")
        Instant expiresAt,

        String username
) {}