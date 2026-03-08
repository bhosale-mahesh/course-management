package com.mb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record InstructorRequest(
        @NotNull(message = "Instructor name is required")
        String name,

        @NotNull(message = "Instructor email is required")
        @Email(message = "Email must be valid")
        String email
) {
}
