package com.mb.dto.response;

import lombok.Builder;

@Builder
public record StudentResponse(
        Long id,
        String name,
        String email
) {
}
