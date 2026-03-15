package com.mb.service;

import com.mb.dto.request.CourseRequest;
import com.mb.dto.response.CourseResponse;
import com.mb.model.Course;
import com.mb.model.Instructor;
import com.mb.repository.CourseRepository;
import com.mb.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    private CourseResponse toCourseResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .instructorName(course.getInstructor().getName())
                .price(course.getPrice())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<CourseResponse> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable).map(this::toCourseResponse);
    }

    @Transactional
    public CourseResponse createCourse(CourseRequest courseRequest) {
        Instructor instructor = instructorRepository.findById(courseRequest.instructorId()).orElseThrow(
                () -> new RuntimeException("Instructor with id " + courseRequest.instructorId() + " not found")
        );
        Course course = Course.builder()
                .title(courseRequest.title())
                .description(courseRequest.description())
                .instructor(instructor)
                .price(courseRequest.price())
                .build();
        Course savedCourse = courseRepository.save(course);
        return toCourseResponse(savedCourse);
    }
}
