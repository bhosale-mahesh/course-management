package com.mb.service;

import com.mb.dto.response.CourseStudentCountResponse;
import com.mb.exception.CourseManagementException;
import com.mb.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public List<CourseStudentCountResponse> getStudentCountPerCourse() {
        return courseRepository.getStudentsPerCourse().stream()
                .map(proj ->
                        CourseStudentCountResponse.builder()
                                .courseId(proj.getCourseId())
                                .courseTitle(proj.getCourseTitle())
                                .studentCount(proj.getStudentCount())
                                .build()
                )
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseStudentCountResponse getStudentCountForCourse(long courseId) {
        CourseStudentCountResponse studentCountForCourse = courseRepository.getStudentCountForCourse(courseId);
        if (studentCountForCourse == null) {
            throw new CourseManagementException("No student found, make sure course exist");
        }
        return studentCountForCourse;
    }
}
