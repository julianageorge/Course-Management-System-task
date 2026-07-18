package com.course_management_system.admin.mapper;

import com.course_management_system.admin.dto.CourseRequest;
import com.course_management_system.admin.dto.CourseResponse;
import com.course_management_system.admin.entity.Course;
import com.course_management_system.admin.entity.Instructor;

public class CourseMapper {
    private CourseMapper() {
    }

    public static void updateEntity(Course course, CourseRequest request, Instructor instructor) {
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setDurationHours(request.getDurationHours());
        course.setRegistrationStartTime(request.getRegistrationStartTime());
        course.setRegistrationEndTime(request.getRegistrationEndTime());
        course.setInstructor(instructor);
    }

    public static CourseResponse toResponse(Course course) {
        Instructor instructor = course.getInstructor();
        return new CourseResponse(course.getId(), course.getTitle(), course.getDescription(), course.getDurationHours(),
                course.isDeleted(), course.getRegistrationStartTime(), course.getRegistrationEndTime(), instructor.getId(),
                instructor.getName());
    }
}





