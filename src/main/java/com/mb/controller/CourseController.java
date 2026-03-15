package com.mb.controller;

import com.mb.dto.response.CourseResponse;
import com.mb.dto.response.PaginatedResponse;
import com.mb.service.CourseService;
import com.mb.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
}
