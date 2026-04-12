package com.mb.service;

import com.mb.dto.request.InstructorRequest;
import com.mb.dto.response.InstructorResponse;
import com.mb.exception.DuplicateResourceException;
import com.mb.model.Instructor;
import com.mb.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;


    @Transactional(readOnly = true)
    public Page<InstructorResponse> getAllInstructors(Pageable pageable) {
        return instructorRepository.findAll(pageable).map(this::toInstructorResponse);
    }

    @Transactional
    public InstructorResponse saveInstructor(InstructorRequest request) {
        if (instructorRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email: " + request.email() + " already exists");
        }
        Instructor instructor = Instructor.builder()
                .name(request.name())
                .email(request.email())
                .build();
        return toInstructorResponse(instructorRepository.save(instructor));
    }

    @Transactional
    public InstructorResponse updateInstructor(Long id, InstructorRequest request) {
        Instructor instructor = getInstructorOrThrow(id);
        instructor.setName(request.name());
        instructor.setEmail(request.email());
        Instructor updatedInstructor = instructorRepository.save(instructor);
        return toInstructorResponse(updatedInstructor);
    }

    public InstructorResponse getInstructorById(Long id) {
        Instructor instructor = getInstructorOrThrow(id);
        return toInstructorResponse(instructor);
    }

    @Transactional(readOnly = true)
    public Instructor getInstructorOrThrow(Long id) {
        return instructorRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Instructor with id " + id + " not found")
        );
    }

    @Transactional(readOnly = true)
    public Page<InstructorResponse> getInstructorsByName(String name, Pageable pageable) {
        return instructorRepository.findByNameContainingIgnoreCase(name, pageable).map(this::toInstructorResponse);
    }

    private InstructorResponse toInstructorResponse(Instructor instructor) {
        return InstructorResponse.builder()
                .id(instructor.getId())
                .name(instructor.getName())
                .email(instructor.getEmail())
                .build();
    }
}
