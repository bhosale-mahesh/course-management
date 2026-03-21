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

import java.math.BigDecimal;

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
        Instructor instructor = getInstructorOrThrow(courseRequest.instructorId());
        Course course = Course.builder()
                .title(courseRequest.title())
                .description(courseRequest.description())
                .instructor(instructor)
                .price(courseRequest.price())
                .build();
        Course savedCourse = courseRepository.save(course);
        return toCourseResponse(savedCourse);
    }

    private Instructor getInstructorOrThrow(Long id) {
        return instructorRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Instructor with id " + id + " not found")
        );
    }

    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Long id) {
        Course course = getCourseOrThrow(id);
        return toCourseResponse(course);
    }

    private Course getCourseOrThrow(Long id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Course with id: " + id + " not found")
        );
    }

    @Transactional
    public CourseResponse updateCourse(Long id, CourseRequest courseRequest) {
        Course course = getCourseOrThrow(id);
        Instructor instructor = getInstructorOrThrow(courseRequest.instructorId());
        course.setTitle(courseRequest.title());
        course.setDescription(courseRequest.description());
        course.setInstructor(instructor);
        course.setPrice(courseRequest.price());
        Course updatedCourse = courseRepository.save(course);
        return toCourseResponse(updatedCourse);
    }

    @Transactional(readOnly = true)
    public Page<CourseResponse> searchByPrice(BigDecimal maxPrice, Pageable pageable) {
        return courseRepository.findByPriceLessThanEqual(maxPrice, pageable).map(this::toCourseResponse);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = getCourseOrThrow(id);
        if (!course.getStudents().isEmpty()) {
            throw new RuntimeException("Cannot delete: Students still enrolled for this course");
        }
        courseRepository.delete(course);
    }
}
