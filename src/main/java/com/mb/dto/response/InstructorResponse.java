package com.mb.dto.response;

import lombok.Builder;

@Builder
public record InstructorResponse(
        Long id,
        String name,
        String email
) {
}
