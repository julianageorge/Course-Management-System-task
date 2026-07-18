package com.course_management_system.admin.service;
import com.course_management_system.admin.dto.StudentRequest;
import com.course_management_system.admin.dto.StudentResponse;
import java.util.List;
public interface StudentService {
    StudentResponse create(StudentRequest request);

    List<StudentResponse> getAll();

    StudentResponse getById(Long id);
    StudentResponse update(Long id, StudentRequest request);
    void delete(Long id);
    
}





