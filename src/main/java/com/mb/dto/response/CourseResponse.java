package com.mb.dto.response;

import java.math.BigDecimal;

public record CourseResponse(
        Long id,
        String title,
        String description,
        BigDecimal price,
        String instructorName
) {
}
