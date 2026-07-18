package com.course_management_system.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SystemReportResponse {
    private long instructors;
    private long students;
    private long activeCourses;
    private long deletedCourses;
    private long enrollments;
}
