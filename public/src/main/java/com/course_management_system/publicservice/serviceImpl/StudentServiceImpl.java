package com.course_management_system.publicservice.serviceImpl;

import com.course_management_system.publicservice.dto.StudentRequest;
import com.course_management_system.publicservice.dto.StudentResponse;
import com.course_management_system.publicservice.entity.Student;
import com.course_management_system.publicservice.exceptions.ResourceNotFoundException;
import com.course_management_system.publicservice.mapper.StudentMapper;
import com.course_management_system.publicservice.repository.StudentRepository;
import com.course_management_system.publicservice.service.StudentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public StudentResponse create(StudentRequest request) {
        Student student = new Student();
        StudentMapper.updateEntity(student, request);
        return StudentMapper.toResponse(studentRepository.save(student));
    }

    @Override
    public List<StudentResponse> getAll() {
        return studentRepository.findAll().stream().map(StudentMapper::toResponse).toList();
    }

    @Override
    public StudentResponse getById(Long id) {
        return StudentMapper.toResponse(findStudent(id));
    }

    @Override
    public StudentResponse update(Long id, StudentRequest request) {
        Student student = findStudent(id);
        StudentMapper.updateEntity(student, request);
        return StudentMapper.toResponse(studentRepository.save(student));
    }

    @Override
    public void delete(Long id) {
        studentRepository.delete(findStudent(id));
    }

    private Student findStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
    }
}





