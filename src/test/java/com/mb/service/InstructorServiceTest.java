package com.mb.service;

import com.mb.dto.request.InstructorRequest;
import com.mb.dto.response.InstructorResponse;
import com.mb.exception.DuplicateResourceException;
import com.mb.exception.ResourceNotFoundException;
import com.mb.model.Course;
import com.mb.model.Instructor;
import com.mb.repository.InstructorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstructorServiceTest {

    @InjectMocks
    InstructorService instructorService;

    @Mock
    InstructorRepository instructorRepository;

    private List<Instructor> getInstructorsList() {
        return List.of(
                Instructor.builder()
                        .id(1L)
                        .name("Minerva McGonagall")
                        .email("mcgonagall@hogwarts.edu.in")
                        .courses(List.of(Course.builder()
                                .id(1L)
                                .title("Transfiguration Mastery")
                                .description("Learn advanced transformation spells")
                                .build()))
                        .build()
        );
    }

    private Page<Instructor> getInstructors() {
        List<Instructor> instructors = getInstructorsList();
        return new PageImpl<>(
                instructors,
                PageRequest.of(0, 10),
                instructors.size()
        );
    }

    private void mockInstructorById() {
        Instructor instructor = getInstructor();
        when(instructorRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(instructor));
    }

    private static Instructor getInstructor() {
        return Instructor.builder()
                .id(1L)
                .name("Minerva McGonagall")
                .email("mcgonagall@hogwarts.edu.in")
                .courses(List.of(Course.builder()
                        .id(1L)
                        .title("Transfiguration Mastery")
                        .description("Learn advanced transformation spells")
                        .build()))
                .build();
    }

    private void mockSaveInstructor(Instructor instructor) {
        when(instructorRepository.save(any(Instructor.class)))
                .thenReturn(instructor);
    }

    @Test
    void getAllInstructors() {
        when(instructorRepository.findAll(any(Pageable.class)))
                .thenReturn(getInstructors());

        Page<InstructorResponse> instructors = instructorService.getAllInstructors(PageRequest.of(0, 10));

        assertNotNull(instructors);
        assertFalse(instructors.getContent().isEmpty());
    }

    @Test
    void saveInstructor() {
        when(instructorRepository.existsByEmail(anyString()))
                .thenReturn(false);
        mockSaveInstructor(getInstructorsList().getFirst());

        InstructorResponse instructor = instructorService.saveInstructor(new InstructorRequest(
                "Severus Snape",
                "snape@hogwarts.edu.in"
        ));

        assertNotNull(instructor);
    }

    @Test
    void saveInstructorException() {
        when(instructorRepository.existsByEmail(anyString()))
                .thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> instructorService.saveInstructor(new InstructorRequest(
                "Severus Snape",
                "snape@hogwarts.edu.in"
        )));
    }

    @Test
    void updateInstructor() {
        mockInstructorById();
        Instructor instructor = Instructor.builder()
                .id(1L)
                .name("test-instructor")
                .email("test@hogwarts.edu.in")
                .courses(List.of(Course.builder()
                        .id(1L)
                        .title("test")
                        .build()))
                .build();
        mockSaveInstructor(instructor);

        InstructorResponse updatedInstructor = instructorService.updateInstructor(1L, new InstructorRequest("test-instructor", "test@hogwarts.edu.in"));
        assertNotNull(updatedInstructor);
        assertEquals("test-instructor", updatedInstructor.name());
    }

    @Test
    void getInstructorById() {
        mockInstructorById();

        InstructorResponse instructor = instructorService.getInstructorById(1L);

        assertNotNull(instructor);
    }

    @Test
    void getInstructorOrThrow() {
        assertThrows(ResourceNotFoundException.class, () -> instructorService.getInstructorOrThrow(1L));
    }

    @Test
    void getInstructorsByName() {
        when(instructorRepository.findByNameContainingIgnoreCase(anyString(), any(Pageable.class)))
                .thenReturn(getInstructors());

        Page<InstructorResponse> instructors = instructorService.getInstructorsByName("Minerva", Pageable.ofSize(1));

        assertNotNull(instructors);
    }
}