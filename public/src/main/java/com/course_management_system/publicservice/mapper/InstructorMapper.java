package com.course_management_system.publicservice.mapper;

import com.course_management_system.publicservice.dto.InstructorRequest;
import com.course_management_system.publicservice.dto.InstructorResponse;
import com.course_management_system.publicservice.entity.Instructor;

public class InstructorMapper {
    private InstructorMapper() {
    }

    public static void updateEntity(Instructor instructor, InstructorRequest request) {
        instructor.setName(request.getName());
        instructor.setEmail(request.getEmail());
        instructor.setExpertise(request.getExpertise());
    }

    public static InstructorResponse toResponse(Instructor instructor) {
        return new InstructorResponse(instructor.getId(), instructor.getName(), instructor.getEmail(),
                instructor.getExpertise());
    }
}





