package com.mb.repository;

import com.mb.dto.response.CourseStudentCountProjection;
import com.mb.dto.response.CourseStudentCountResponse;
import com.mb.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByPriceLessThanEqual(BigDecimal maxPrice, Pageable pageable);

    /*
    @Query(value = """
                    select c.id as courseId, c.title as courseTitle, count(sc.student_id) as studentCount
                    from course c left join student_course sc on c.id = sc.course_id
                    group by c.id
                    order by c.id
            """, nativeQuery = true)
    List<CourseStudentCountResponse> getStudentsPerCourse(); // Use native query
    */

    @Query(value = """
                 select c.id as courseId, c.title as courseTitle, count(s) as studentCount
                 from Course c left join c.students s
                 group by c.id
                 order by c.id
            """)
    List<CourseStudentCountProjection> getStudentsPerCourse();
}
