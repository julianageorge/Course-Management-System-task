package com.course_management_system.admin.serviceImpl;

import com.course_management_system.admin.dto.SystemReportResponse;
import com.course_management_system.admin.repository.CourseRepository;
import com.course_management_system.admin.repository.EnrollmentRepository;
import com.course_management_system.admin.repository.InstructorRepository;
import com.course_management_system.admin.repository.StudentRepository;
import com.course_management_system.admin.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public SystemReportResponse getSystemReport() {
        return new SystemReportResponse(
                instructorRepository.count(),
                studentRepository.count(),
                courseRepository.countByDeletedFalse(),
                courseRepository.countByDeletedTrue(),
                enrollmentRepository.count());
    }
}
