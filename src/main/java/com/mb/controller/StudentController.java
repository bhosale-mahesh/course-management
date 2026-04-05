package com.mb.controller;

import com.mb.dto.request.StudentRequest;
import com.mb.dto.response.CourseResponse;
import com.mb.dto.response.PaginatedResponse;
import com.mb.dto.response.StudentResponse;
import com.mb.service.StudentService;
import com.mb.util.PaginationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public PaginatedResponse<StudentResponse> getAllStudents(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = PaginationUtil.DEFAULT_PAGE_SIZE) int size) {
        return PaginationUtil.buildPaginatedResponse(
                studentService.getAllStudents(PageRequest.of(page, size, Sort.by("id")))
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse saveStudent(@Valid @RequestBody StudentRequest request) {
        return studentService.saveStudent(request);
    }

    @PutMapping("/{id}")
    public StudentResponse updateStudent(@PathVariable("id") Long id,
                                         @Valid @RequestBody StudentRequest request) {
        return studentService.updateStudent(id, request);
    }

    @GetMapping("/{id}")
    public StudentResponse getStudentById(@PathVariable("id") Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/find")
    public PaginatedResponse<StudentResponse> getStudentsByName(@RequestParam("name") String name,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = PaginationUtil.DEFAULT_PAGE_SIZE) int size) {
        return PaginationUtil.buildPaginatedResponse(
                studentService.getStudentsByName(name, PageRequest.of(page, size, Sort.by("id")))
        );
    }

    @PostMapping("/{studentId}/enroll/{courseId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void enrollStudent(@PathVariable("studentId") Long studentId,
                              @PathVariable("courseId") Long courseId) {
        studentService.enrollStudent(studentId, courseId);
    }

    @DeleteMapping("/{studentId}/course/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dropOutOfCourse(@PathVariable("studentId") Long studentId,
                              @PathVariable("courseId") Long courseId) {
        studentService.dropOutOfCourse(studentId, courseId);
    }

    @GetMapping("/{studentId}/courses")
    public List<CourseResponse> getEnrolledCourses(@PathVariable("studentId") Long studentId) {
        return studentService.getEnrolledCourses(studentId);
    }
}
