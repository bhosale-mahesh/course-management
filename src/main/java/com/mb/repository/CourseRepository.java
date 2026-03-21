package com.mb.repository;

import com.mb.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByPriceLessThanEqual(BigDecimal maxPrice, Pageable pageable);
}
