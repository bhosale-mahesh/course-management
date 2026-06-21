package com.mb.controller;

import com.mb.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @MockitoBean
    CourseService courseService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllCourses() {
    }

    @Test
    void createCourse() {
    }

    @Test
    void getCourseById() {
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