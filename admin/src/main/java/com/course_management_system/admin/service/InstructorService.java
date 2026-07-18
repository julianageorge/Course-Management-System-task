package com.course_management_system.admin.service;
import com.course_management_system.admin.dto.InstructorRequest;
import com.course_management_system.admin.dto.InstructorResponse;
import java.util.List;

public interface InstructorService {
    InstructorResponse create(InstructorRequest request);

    List<InstructorResponse> getAll();

    InstructorResponse getById(Long id);

    InstructorResponse update(Long id, InstructorRequest request);

    void delete(Long id);
}




