package com.course_management_system.admin.service;
import  com.course_management_system.admin.dto.EnrollmentRequest;
import  com.course_management_system.admin.dto.EnrollmentResponse;
import java.util.List;
public interface EnrollmentService {
    EnrollmentResponse enroll(EnrollmentRequest request);

    List<EnrollmentResponse> getAll();

    EnrollmentResponse getById(Long id);

    void delete(Long id);
    
}





