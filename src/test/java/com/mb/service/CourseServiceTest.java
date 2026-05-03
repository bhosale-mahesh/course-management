package com.mb.service;

import com.mb.dto.request.CourseRequest;
import com.mb.dto.response.CourseResponse;
import com.mb.model.Course;
import com.mb.model.Instructor;
import com.mb.model.Student;
import com.mb.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @InjectMocks
    CourseService courseService;

    @Mock
    CourseRepository courseRepository;

    @Mock
    InstructorService instructorService;

    private List<Course> getCoursesList() {
        return List.of(
                Course.builder()
                        .id(1L)
                        .title("Advanced Potion Making")
                        .description("Master the art of brewing magical potions")
                        .price(BigDecimal.valueOf(120L))
                        .isActive(true)
                        .instructor(Instructor.builder().id(1L).build())
                        .build()
        );
    }

    private Page<Course> getCourses() {
        List<Course> courses = getCoursesList();
        return new PageImpl<>(
                courses,
                PageRequest.of(0, 10),
                courses.size()
        );
    }

    @Test
    void getAllCourses() {
        when(courseRepository.findAll(any(Pageable.class)))
                .thenReturn(getCourses());

        Page<CourseResponse> allCourses = courseService.getAllCourses(Pageable.ofSize(1));

        assertNotNull(allCourses);
    }

    @Test
    void createCourse() {
        when(instructorService.getInstructorOrThrow(anyLong()))
                .thenReturn(Instructor.builder().id(1L).build());
        when(courseRepository.save(any(Course.class)))
                .thenReturn(getCoursesList().getFirst());

        CourseResponse course = courseService.createCourse(new CourseRequest("test-title", "test-description", BigDecimal.valueOf(100L), 1L));

        assertNotNull(course);
    }

    @Test
    void getCourseById() {
    }

    @Test
    void getCourseOrThrow() {
    }

    @Test
    void updateCourse() {
    }

    @Test
    void searchByPrice() {
    }

    @Test
    void deleteCourse() {
    }
}