package com.course_management_system.admin.mapper;

import com.course_management_system.admin.dto.EnrollmentResponse;
import com.course_management_system.admin.entity.Course;
import com.course_management_system.admin.entity.Enrollment;
import com.course_management_system.admin.entity.Student;

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





