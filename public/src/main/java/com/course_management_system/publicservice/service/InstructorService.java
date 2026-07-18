package com.course_management_system.publicservice.service;
import com.course_management_system.publicservice.dto.InstructorRequest;
import com.course_management_system.publicservice.dto.InstructorResponse;
import java.util.List;

public interface InstructorService {
    InstructorResponse create(InstructorRequest request);

    List<InstructorResponse> getAll();

    InstructorResponse getById(Long id);

    InstructorResponse update(Long id, InstructorRequest request);

    void delete(Long id);
}




