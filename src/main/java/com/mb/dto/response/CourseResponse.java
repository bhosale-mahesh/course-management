package com.mb.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CourseResponse(
        Long id,
        String title,
        String description,
        BigDecimal price,
        String instructorName
) {
}
