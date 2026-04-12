package com.mb.repository;

import com.mb.dto.response.CourseStudentCountProjection;
import com.mb.dto.response.CourseStudentCountResponse;
import com.mb.model.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    CourseRepository courseRepository;

    @Test
    void findByPriceLessThanEqual() {
        Page<Course> courses = courseRepository.findByPriceLessThanEqual(BigDecimal.valueOf(100), Pageable.ofSize(1));
        assertTrue(courses.getSize() > 0);
    }

    @Test
    void getStudentsPerCourse() {
        List<CourseStudentCountProjection> studentsPerCourse = courseRepository.getStudentsPerCourse();
        assertNotNull(studentsPerCourse);
        assertFalse(studentsPerCourse.isEmpty());
    }

    @Test
    void getStudentCountForCourse() {
        CourseStudentCountResponse studentCountForCourse = courseRepository.getStudentCountForCourse(1);
        assertNotNull(studentCountForCourse);
        assertTrue(studentCountForCourse.studentCount() > 0);
    }
}