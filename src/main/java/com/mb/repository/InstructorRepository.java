package com.mb.repository;

import com.mb.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    boolean existsByEmail(String email);
}
