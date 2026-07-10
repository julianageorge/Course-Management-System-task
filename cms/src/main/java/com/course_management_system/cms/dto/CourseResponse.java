package com.course_management_system.cms.dto;

public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private Integer durationHours;
    private boolean deleted;
    private Long instructorId;
    private String instructorName;
    public CourseResponse() {
    }

public CourseResponse(Long id, String title, String description, Integer durationHours, boolean deleted,
            Long instructorId, String instructorName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.durationHours = durationHours;
        this.deleted = deleted;
        this.instructorId = instructorId;
        this.instructorName = instructorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
}