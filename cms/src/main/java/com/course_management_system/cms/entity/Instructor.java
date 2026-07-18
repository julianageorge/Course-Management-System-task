package com.course_management_system.cms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String expertise;

    @JsonIgnore
    @OneToMany(mappedBy = "instructor")
    private List<Course> courses = new ArrayList<>();

    public Instructor(Long id, String name, String email, String expertise) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.expertise = expertise;
    }
}
