package com.mb.service;

import com.mb.dto.request.StudentRequest;
import com.mb.dto.response.CourseResponse;
import com.mb.dto.response.StudentResponse;
import com.mb.model.Course;
import com.mb.model.Student;
import com.mb.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseService courseService;

    private StudentResponse toStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<StudentResponse> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).map(this::toStudentResponse);
    }

    @Transactional
    public StudentResponse saveStudent(StudentRequest request) {
        if (studentRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email: " + request.email() + " already exist");
        }
        Student newStudent = Student.builder()
                .name(request.name())
                .email(request.email())
                .build();
        Student savedStudent = studentRepository.save(newStudent);
        return toStudentResponse(savedStudent);
    }

    @Transactional
    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student studentToUpdate = getStudentOrThrow(id);
        studentToUpdate.setName(request.name());
        studentToUpdate.setEmail(request.email());
        Student updatedStudent = studentRepository.save(studentToUpdate);
        return toStudentResponse(updatedStudent);
    }

    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id) {
        Student student = getStudentOrThrow(id);
        return toStudentResponse(student);
    }

    private Student getStudentOrThrow(Long id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Student not found with id " + id)
        );
    }

    @Transactional(readOnly = true)
    public Page<StudentResponse> getStudentsByName(String name, Pageable pageable) {
        return studentRepository.findByNameContainingIgnoreCase(name, pageable).map(this::toStudentResponse);
    }

    @Transactional
    public void enrollStudent(Long studentId, Long courseId) {
        Student student = getStudentOrThrow(studentId);
        Course course = courseService.getCourseOrThrow(courseId);
        if (student.getCourses().contains(course)) {
            throw new RuntimeException("Student with id " + studentId + " is already enrolled in course");
        }
        student.getCourses().add(course);
        studentRepository.save(student);
    }

    @Transactional
    public void dropOutOfCourse(Long studentId, Long courseId) {
        Student student = getStudentOrThrow(studentId);
        Course course = courseService.getCourseOrThrow(courseId);
        if (!student.getCourses().contains(course)) {
            throw new RuntimeException("Student with id " + studentId + " is not enrolled in course");
        }
        student.getCourses().remove(course);
        studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getEnrolledCourses(Long studentId) {
        Student student = getStudentOrThrow(studentId);
        return student.getCourses().stream().map(
                course -> CourseResponse.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .description(course.getDescription())
                        .instructorName(course.getInstructor().getName())
                        .price(course.getPrice())
                        .build()
        ).toList();
    }
}
