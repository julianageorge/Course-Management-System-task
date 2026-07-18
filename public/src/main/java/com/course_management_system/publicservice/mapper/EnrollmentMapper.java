package com.course_management_system.publicservice.mapper;

import com.course_management_system.publicservice.dto.EnrollmentResponse;
import com.course_management_system.publicservice.entity.Course;
import com.course_management_system.publicservice.entity.Enrollment;
import com.course_management_system.publicservice.entity.Student;

public class EnrollmentMapper {
    private EnrollmentMapper() {
    }

    public static EnrollmentResponse toResponse(Enrollment enrollment) {
        Student student = enrollment.getStudent();
        Course course = enrollment.getCourse();
        return new EnrollmentResponse(enrollment.getId(), student.getId(), student.getName(), course.getId(),
                course.getTitle(), enrollment.getEnrolledAt());
    }
}





