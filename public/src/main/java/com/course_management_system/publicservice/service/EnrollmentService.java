package com.course_management_system.publicservice.service;
import  com.course_management_system.publicservice.dto.EnrollmentRequest;
import  com.course_management_system.publicservice.dto.EnrollmentResponse;
import java.util.List;
public interface EnrollmentService {
    EnrollmentResponse enroll(EnrollmentRequest request);

    List<EnrollmentResponse> getAll();

    EnrollmentResponse getById(Long id);

    void delete(Long id);
    
}





