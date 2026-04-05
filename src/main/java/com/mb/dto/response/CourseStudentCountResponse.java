package com.mb.dto.response;

import lombok.Builder;

@Builder
public record CourseStudentCountResponse(
        Long courseId,
        String courseTitle,
        Long studentCount
) {
}
