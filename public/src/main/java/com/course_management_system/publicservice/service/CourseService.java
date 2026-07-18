package com.course_management_system.publicservice.service;

import com.course_management_system.publicservice.dto.CourseResponse;
import com.course_management_system.publicservice.dto.CourseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface CourseService {
    CourseResponse create (CourseRequest request);
    Page<CourseResponse> getAllCourses(Pageable pageable);
    CourseResponse getById(Long id);
    CourseResponse update(Long id, CourseRequest request);

    void softDelete(Long id);
}





