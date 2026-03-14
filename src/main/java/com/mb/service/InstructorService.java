package com.mb.service;

import com.mb.dto.request.InstructorRequest;
import com.mb.dto.response.InstructorResponse;
import com.mb.model.Instructor;
import com.mb.repository.InstructorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;

    private InstructorResponse toInstructorResponse(Instructor instructor) {
        return InstructorResponse.builder()
                .id(instructor.getId())
                .name(instructor.getName())
                .email(instructor.getEmail())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<InstructorResponse> getAllInstructors(Pageable pageable) {
        return instructorRepository.findAll(pageable).map(this::toInstructorResponse);
    }

    @Transactional
    public InstructorResponse saveInstructor(InstructorRequest request) {
        if (instructorRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email: " + request.email() + " already exists");
        }
        Instructor instructor = Instructor.builder()
                .name(request.name())
                .email(request.email())
                .build();
        return toInstructorResponse(instructorRepository.save(instructor));
    }

}
