package com.course_management_system.cms.service;

import com.course_management_system.cms.dto.CourseResponse;
import com.course_management_system.cms.dto.CourseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface CourseService {
    CourseResponse create (CourseRequest request);
    Page<CourseResponse> getAllCourses(Pageable pageable);
    CourseResponse getById(Long id);
    CourseResponse update(Long id, CourseRequest request);

    void softDelete(Long id);
}
