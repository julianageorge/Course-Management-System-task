package com.course_management_system.publicservice.mapper;

import com.course_management_system.publicservice.dto.StudentRequest;
import com.course_management_system.publicservice.dto.StudentResponse;
import com.course_management_system.publicservice.entity.Student;

public class StudentMapper {
    private StudentMapper() {
    }

    public static void updateEntity(Student student, StudentRequest request) {
        student.setName(request.getName());
        student.setEmail(request.getEmail());
    }

    public static StudentResponse toResponse(Student student) {
        return new StudentResponse(student.getId(), student.getName(), student.getEmail());
    }
}





