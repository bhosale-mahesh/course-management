package com.mb.service;

import com.mb.dto.request.StudentRequest;
import com.mb.dto.response.StudentResponse;
import com.mb.model.Student;
import com.mb.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

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
        Student studentToUpdate = studentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Student with id " + id + " not found")
        );
        studentToUpdate.setName(request.name());
        studentToUpdate.setEmail(request.email());
        Student updatedStudent = studentRepository.save(studentToUpdate);
        return toStudentResponse(updatedStudent);
    }

    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Student not found with id " + id));
        return toStudentResponse(student);
    }

    @Transactional(readOnly = true)
    public Page<StudentResponse> getStudentsByName(String name, Pageable pageable) {
        return studentRepository.findByNameContainingIgnoreCase(name, pageable).map(this::toStudentResponse);
    }

    private StudentResponse toStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .build();
    }
}
