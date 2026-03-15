package com.mb.controller;

import com.mb.dto.request.CourseRequest;
import com.mb.dto.response.CourseResponse;
import com.mb.dto.response.PaginatedResponse;
import com.mb.service.CourseService;
import com.mb.util.PaginationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public PaginatedResponse<CourseResponse> getAllCourses(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = PaginationUtil.DEFAULT_PAGE_SIZE) int size) {
        return PaginationUtil.buildPaginatedResponse(
                courseService.getAllCourses(PageRequest.of(page, size, Sort.by("id")))
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse createCourse(@Valid @RequestBody CourseRequest courseRequest) {
        return courseService.createCourse(courseRequest);
    }
}
