package com.course_management_system.cms.service;
import  com.course_management_system.cms.dto.EnrollmentRequest;
import  com.course_management_system.cms.dto.EnrollmentResponse;
import java.util.List;
public interface EnrollmentService {
    EnrollmentResponse enroll(EnrollmentRequest request);

    List<EnrollmentResponse> getAll();

    EnrollmentResponse getById(Long id);

    void delete(Long id);
    
}
