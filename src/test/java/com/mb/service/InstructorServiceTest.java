package com.mb.service;

import com.mb.dto.response.InstructorResponse;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
    }

    @Test
    void updateInstructor() {
    }

    @Test
    void getInstructorById() {
    }

    @Test
    void getInstructorOrThrow() {
    }

    @Test
    void getInstructorsByName() {
    }
}