package com.anton3413.quiz_hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
@SpringBootApplication()
@ConfigurationPropertiesScan
public class QuizHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizHubApplication.class, args);
    }

}
