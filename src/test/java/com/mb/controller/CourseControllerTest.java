package com.mb.controller;

import com.mb.dto.request.CourseRequest;
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
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
        return List.of(getAdvancedPotionMakingCourse());
    }

    private static CourseResponse getAdvancedPotionMakingCourse() {
        return CourseResponse.builder()
                .id(1L)
                .title("Advanced Potion Making")
                .description("Master the art of brewing magical potions")
                .price(BigDecimal.valueOf(120L))
                .build();
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
    void createCourse() throws Exception {
        when(courseService.createCourse(any(CourseRequest.class)))
                .thenReturn(getAdvancedPotionMakingCourse());

        mockMvc.perform(MockMvcRequestBuilders.post(COURSE_URL)
                        .content(new ObjectMapper().writeValueAsBytes(
                                        new CourseRequest("Advanced Potion Making",
                                                "Master the art of brewing magical potions",
                                                BigDecimal.valueOf(120L),
                                                1L
                                        )
                                )
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getCourseById() throws Exception {
        when(courseService.getCourseById(anyLong()))
                .thenReturn(getAdvancedPotionMakingCourse());

        mockMvc.perform(MockMvcRequestBuilders.get(COURSE_URL + "/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Advanced Potion Making"));
    }

    @Test
    void updateCourse() throws Exception {
        when(courseService.updateCourse(anyLong(), any(CourseRequest.class)))
                .thenReturn(getAdvancedPotionMakingCourse());

        mockMvc.perform(MockMvcRequestBuilders.put(COURSE_URL + "/{id}", 1)
                        .content(new ObjectMapper().writeValueAsBytes(
                                        new CourseRequest("Advanced Potion Making",
                                                "Test",
                                                BigDecimal.valueOf(120L),
                                                1L
                                        )
                                )
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("Master the art of brewing magical potions"));
    }

    @Test
    void searchByPrice() throws Exception {
        when(courseService.searchByPrice(any(BigDecimal.class), any(Pageable.class)))
                .thenReturn(getCourses());

        mockMvc.perform(MockMvcRequestBuilders.get(COURSE_URL + "/search").param("maxPrice", "150")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].title").value("Advanced Potion Making"));
    }

    @Test
    void deleteCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(COURSE_URL + "/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}