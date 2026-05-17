package com.mb.service;

import com.mb.model.Course;
import com.mb.model.Instructor;
import com.mb.repository.InstructorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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

    @Test
    void getAllInstructors() {
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