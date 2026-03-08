package com.mb.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PaginatedResponse<T>(
        List<T> data,
        int page,
        long totalPages,
        long totalItems,
        int pageSize,
        boolean hasNext,
        boolean hasPrevious
) {
}
