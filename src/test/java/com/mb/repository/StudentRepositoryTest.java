package com.mb.repository;

import com.mb.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @Test
    void existsByEmail() {
        assertTrue(studentRepository.existsByEmail("harry@hogwarts.edu.in"));
        assertFalse(studentRepository.existsByEmail("dudley@hogwarts.edu.in"));
    }

    @Test
    void findByNameContainingIgnoreCase() {
        Page<Student> ron = studentRepository.findByNameContainingIgnoreCase("ron", Pageable.ofSize(1));
        assertNotNull(ron);
        assertEquals("Ron Weasley", ron.getContent().getFirst().getName());
    }
}