package com.anton3413.quiz_hub.dto.user;

import com.anton3413.quiz_hub.validation.annotation.PasswordMatches;
import com.anton3413.quiz_hub.validation.annotation.UniqueEmail;
import com.anton3413.quiz_hub.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@PasswordMatches
@Builder
public record CreateUserRequest(
        @NotBlank(message = "Username cannot be empty")
        @Size(min = 3, max = 20, message = "Username: 3-20 characters required")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username: Use only letters, digits and underscores")
        @UniqueUsername
        String username,

        @NotBlank(message = "Password is required")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$",
                message = "Password must be 8-20 characters long and include at least one uppercase letter, one lowercase letter, and one number"
        )
        String password,

        String confirmPassword,

        @NotBlank(message = "Email cannot be empty")
        @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
                message = "Please enter a valid email address")
        @UniqueEmail
        String email
) {
}