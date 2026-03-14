package com.mb.controller;

import com.mb.dto.request.InstructorRequest;
import com.mb.dto.response.InstructorResponse;
import com.mb.dto.response.PaginatedResponse;
import com.mb.service.InstructorService;
import com.mb.util.PaginationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/instructors")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @GetMapping
    public PaginatedResponse<InstructorResponse> getAllInstructors(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = PaginationUtil.DEFAULT_PAGE_SIZE) int size) {
        return PaginationUtil.buildPaginatedResponse(instructorService.getAllInstructors(PageRequest.of(page, size, Sort.by("id"))));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstructorResponse saveInstructor(@Valid @RequestBody InstructorRequest request) {
        return instructorService.saveInstructor(request);
    }

    @PutMapping("/{id}")
    public InstructorResponse updateInstructor(@PathVariable("id") Long id, @Valid @RequestBody InstructorRequest request) {
        return instructorService.updateInstructor(id, request);
    }

    @GetMapping("/{id}")
    public InstructorResponse getInstructorById(@PathVariable("id") Long id) {
        return instructorService.getInstructorById(id);
    }

    @GetMapping("/find")
    public PaginatedResponse<InstructorResponse> getInstructorsByName(@RequestParam("name") String name,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = PaginationUtil.DEFAULT_PAGE_SIZE) int size) {
        return PaginationUtil.buildPaginatedResponse(instructorService.getInstructorsByName(name, PageRequest.of(page, size, Sort.by("id"))));
    }
}
