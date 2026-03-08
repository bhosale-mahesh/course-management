package com.mb.service;

import com.mb.dto.response.StudentResponse;
import com.mb.model.Student;
import com.mb.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream().map(this::toStudentResponse).toList();
    }

    private StudentResponse toStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .build();
    }
}
