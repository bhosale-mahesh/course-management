package com.mb.service;

import com.mb.dto.request.StudentRequest;
import com.mb.dto.response.StudentResponse;
import com.mb.model.Student;
import com.mb.repository.StudentRepository;
import jakarta.validation.Valid;
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
    public StudentResponse saveStudent(@Valid StudentRequest request) {
        if (studentRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email: " + request.email() + " already exist");
        }
        Student newStudent = Student.builder()
                .name(request.name())
                .email(request.email())
                .build();
        Student studentFromDb = studentRepository.save(newStudent);
        return toStudentResponse(studentFromDb);
    }

    private StudentResponse toStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .build();
    }
}
