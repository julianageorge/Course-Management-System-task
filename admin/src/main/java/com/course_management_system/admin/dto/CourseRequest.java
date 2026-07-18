package com.course_management_system.admin.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequest {
    private String title;
    private String description;
    private Integer durationHours;
    private Long instructorId;
    private LocalDateTime registrationStartTime;
    private LocalDateTime registrationEndTime;
}





