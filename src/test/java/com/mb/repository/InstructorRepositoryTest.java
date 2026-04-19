package com.mb.repository;

import com.mb.model.Instructor;
import com.mb.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class InstructorRepositoryTest {

    @Autowired
    InstructorRepository instructorRepository;

    @Test
    void existsByEmail() {
        assertTrue(instructorRepository.existsByEmail("mcgonagall@hogwarts.edu.in"));
        assertFalse(instructorRepository.existsByEmail("vernon@hogwarts.edu.in"));
    }

    @Test
    void findByNameContainingIgnoreCase() {
        Page<Instructor> snape = instructorRepository.findByNameContainingIgnoreCase("snape", Pageable.ofSize(1));
        assertNotNull(snape);
        assertEquals("Severus Snape", snape.getContent().getFirst().getName());
    }
}