package com.mb.service;

import com.mb.dto.response.CourseStudentCountResponse;
import com.mb.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final CourseRepository courseRepository;

    public List<CourseStudentCountResponse> getStudentCountPerCourse() {
        return courseRepository.getStudentsPerCourse();
    }
}
