package com.mb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record StudentRequest(
        @NotEmpty(message = "Student name is required")
        String name,

        @NotEmpty(message = "Student email is required")
        @Email(message = "Email must be valid")
        String email
) {
}
