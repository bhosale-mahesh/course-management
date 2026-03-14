package com.mb.repository;

import com.mb.model.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    boolean existsByEmail(String email);

    Page<Instructor> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
