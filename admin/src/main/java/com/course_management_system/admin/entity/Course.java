package com.course_management_system.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Integer durationHours;
    private boolean deleted;
    private LocalDateTime registrationStartTime;
    private LocalDateTime registrationEndTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Instructor instructor;

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments = new ArrayList<>();

    public Course(Long id, String title, String description, Integer durationHours, boolean deleted,
            Instructor instructor) {
        this(id, title, description, durationHours, deleted, null, null, instructor);
    }

    public Course(Long id, String title, String description, Integer durationHours, boolean deleted,
            LocalDateTime registrationStartTime, LocalDateTime registrationEndTime, Instructor instructor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.durationHours = durationHours;
        this.deleted = deleted;
        this.registrationStartTime = registrationStartTime;
        this.registrationEndTime = registrationEndTime;
        this.instructor = instructor;
    }
}





