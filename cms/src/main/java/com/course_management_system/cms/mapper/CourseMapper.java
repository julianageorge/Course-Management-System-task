package com.course_management_system.cms.mapper;

import com.course_management_system.cms.dto.CourseRequest;
import com.course_management_system.cms.dto.CourseResponse;
import com.course_management_system.cms.entity.Course;
import com.course_management_system.cms.entity.Instructor;

public class CourseMapper {
    private CourseMapper() {
    }

    public static void updateEntity(Course course, CourseRequest request, Instructor instructor) {
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setDurationHours(request.getDurationHours());
        course.setRegistrationStart(request.getRegistrationStart());
        course.setRegistrationEnd(request.getRegistrationEnd());
        course.setInstructor(instructor);
    }

    public static CourseResponse toResponse(Course course) {
        Instructor instructor = course.getInstructor();
        return new CourseResponse(course.getId(), course.getTitle(), course.getDescription(), course.getDurationHours(),
                course.isDeleted(), course.getRegistrationStart(), course.getRegistrationEnd(), instructor.getId(),
                instructor.getName());
    }
}
