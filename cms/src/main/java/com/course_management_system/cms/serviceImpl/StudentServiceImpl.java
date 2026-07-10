package com.course_management_system.cms.serviceImpl;
import org.springframework.stereotype.Service;
import com.course_management_system.cms.repository.StudentRepository;
import com.course_management_system.cms.dto.StudentRequest;
import com.course_management_system.cms.dto.StudentResponse;
import com.course_management_system.cms.entity.Student;
import com.course_management_system.cms.exceptions.ResourceNotFoundException;
import com.course_management_system.cms.service.StudentService;
import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentResponse create(StudentRequest request) {
        Student student = new Student();
        applyRequest(student, request);
        return toResponse(studentRepository.save(student));
    }

    @Override
    public List<StudentResponse> getAll() {
        return studentRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public StudentResponse getById(Long id) {
        return toResponse(findStudent(id));
    }

    @Override
    public StudentResponse update(Long id, StudentRequest request) {
        Student student = findStudent(id);
        applyRequest(student, request);
        return toResponse(studentRepository.save(student));
    }

    @Override
    public void delete(Long id) {
        studentRepository.delete(findStudent(id));
    }

    private Student findStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
    }

    private void applyRequest(Student student, StudentRequest request) {
        student.setName(request.getName());
        student.setEmail(request.getEmail());
    }

    private StudentResponse toResponse(Student student) {
        return new StudentResponse(student.getId(), student.getName(), student.getEmail());
    }
}
