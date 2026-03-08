package com.mb.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CourseRequest(
        @NotEmpty(message = "Course title is required")
        String title,

        String description,

        @NotNull(message = "Course price is required")
        @DecimalMin(value = "0.0", message = "Price must be positive")
        BigDecimal price,

        @NotNull(message = "Instructor ID is required")
        Long instructorId
) {
}
