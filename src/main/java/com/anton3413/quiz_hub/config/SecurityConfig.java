package com.anton3413.quiz_hub.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ConfigurationProperties(prefix = "app.security")
@Getter
@Setter
public class SecurityConfig {

    private int tokenExpiration;

    private String baseUrl;

    @Bean
    public PasswordEncoder createPasswordEncoder(){
       return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
