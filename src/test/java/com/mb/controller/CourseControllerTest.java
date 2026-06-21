package com.mb.controller;

import com.mb.dto.response.CourseResponse;
import com.mb.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CourseService courseService;

    private static final String COURSE_URL = "/v1/api/courses";

    private List<CourseResponse> getCoursesList() {
        return List.of(
                CourseResponse.builder()
                        .id(1L)
                        .title("Advanced Potion Making")
                        .description("Master the art of brewing magical potions")
                        .price(BigDecimal.valueOf(120L))
                        .build()
        );
    }

    private Page<CourseResponse> getCourses() {
        List<CourseResponse> courses = getCoursesList();
        return new PageImpl<>(
                courses,
                PageRequest.of(0, 10),
                courses.size()
        );
    }

    @Test
    void getAllCourses() throws Exception {
        when(courseService.getAllCourses(any(Pageable.class)))
                .thenReturn(getCourses());

        mockMvc.perform(MockMvcRequestBuilders.get(COURSE_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].title").value("Advanced Potion Making"));
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