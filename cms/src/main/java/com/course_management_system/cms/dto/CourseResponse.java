package com.course_management_system.cms.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private Integer durationHours;
    private boolean deleted;
    private LocalDateTime registrationStart;
    private LocalDateTime registrationEnd;
    private Long instructorId;
    private String instructorName;
}
