package com.mb.controller;

import com.mb.dto.response.CourseStudentCountResponse;
import com.mb.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/courses/student-count")
    public List<CourseStudentCountResponse> getStudentCountPerCourse() {
        return reportService.getStudentCountPerCourse();
    }
}
