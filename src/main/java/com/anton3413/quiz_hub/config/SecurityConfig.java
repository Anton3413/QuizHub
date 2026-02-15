package com.anton3413.quiz_hub.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.security")
@Getter
@Setter
public class SecurityConfig {

    private int tokenExpiration;

    private String baseUrl;

}
