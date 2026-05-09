package com.mb.service;

import com.mb.dto.request.CourseRequest;
import com.mb.dto.response.CourseResponse;
import com.mb.exception.CourseManagementException;
import com.mb.exception.ResourceNotFoundException;
import com.mb.model.Course;
import com.mb.model.Instructor;
import com.mb.model.Student;
import com.mb.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
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

    private void mockCourseById() {
        Instructor instructor = Instructor.builder()
                .id(1L)
                .name("Minerva McGonagall")
                .build();
        when(courseRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(Course.builder().instructor(instructor).build()));
    }

    private void mockInstructorById() {
        when(instructorService.getInstructorOrThrow(anyLong()))
                .thenReturn(Instructor.builder().id(1L).build());
    }

    private void mockSaveCourse(Course course) {
        when(courseRepository.save(any(Course.class)))
                .thenReturn(course);
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
        mockInstructorById();
        mockSaveCourse(getCoursesList().getFirst());

        CourseResponse course = courseService.createCourse(new CourseRequest("test-title", "test-description", BigDecimal.valueOf(100L), 1L));

        assertNotNull(course);
    }



    @Test
    void getCourseById() {
        mockCourseById();

        CourseResponse course = courseService.getCourseById(1L);

        assertNotNull(course);
    }


    @Test
    void getCourseOrThrowResourceNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> courseService.getCourseOrThrow(1L));
    }

    @Test
    void updateCourse() {
        mockCourseById();
        mockInstructorById();
        Course testCourse = Course.builder()
                .id(1L)
                .title("test-title")
                .description("new-description")
                .price(BigDecimal.valueOf(120L))
                .isActive(true)
                .instructor(Instructor.builder().id(1L).build())
                .build();
        mockSaveCourse(testCourse);

        CourseResponse updatedCourse = courseService.updateCourse(1L, new CourseRequest("test-title", "new-description", BigDecimal.valueOf(120L), 1L));

        assertNotNull(updatedCourse);
        assertEquals("new-description", updatedCourse.description());
    }

    @Test
    void searchByPrice() {
        when(courseRepository.findByPriceLessThanEqual(any(BigDecimal.class), any(Pageable.class)))
                .thenReturn(getCourses());

        courseService.searchByPrice(BigDecimal.valueOf(120L), Pageable.ofSize(1));
    }

    @Test
    void deleteCourse() {
        Course course = getCoursesList().getFirst();
        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        courseService.deleteCourse(1L);

        verify(courseRepository, Mockito.atLeastOnce()).delete(course);
    }

    @Test
    void deleteCourseException() {
        Course course = getCoursesList().getFirst();
        course.setStudents(Set.of(Student.builder().id(1L).build()));
        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        assertThrows(CourseManagementException.class, () -> courseService.deleteCourse(1L));
    }

}