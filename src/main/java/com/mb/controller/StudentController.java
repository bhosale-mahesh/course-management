package com.mb.controller;

import com.mb.dto.response.PaginatedResponse;
import com.mb.dto.response.StudentResponse;
import com.mb.service.StudentService;
import com.mb.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public PaginatedResponse<StudentResponse> getAllStudents(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = PaginationUtil.DEFAULT_PAGE_SIZE) int size) {
        return PaginationUtil.buildPaginatedResponse(studentService.getAllStudents(PageRequest.of(page, size)));
    }
}
