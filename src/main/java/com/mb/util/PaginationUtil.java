package com.mb.util;

import com.mb.dto.response.PaginatedResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaginationUtil {

    public static final String DEFAULT_PAGE_SIZE = "10";

    public static <T> PaginatedResponse<T> buildPaginatedResponse(Page<T> page) {
        return PaginatedResponse.<T>builder()
                .data(page.getContent())
                .page(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalItems(page.getTotalElements())
                .pageSize(page.getSize())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }
}
