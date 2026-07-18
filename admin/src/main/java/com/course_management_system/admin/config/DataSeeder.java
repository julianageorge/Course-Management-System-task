package com.course_management_system.admin.config;

import com.course_management_system.admin.entity.Course;
import com.course_management_system.admin.entity.Instructor;
import com.course_management_system.admin.repository.CourseRepository;
import com.course_management_system.admin.repository.InstructorRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;

    @Override
    public void run(String... args) {
        if (instructorRepository.existsByEmail("george@gmail.com")) {
            return;
        }

        Instructor instructor = instructorRepository.save(
                new Instructor(null, "George", "george@gmail.com", "Java and Spring Boot"));

        Course course = new Course(null, "Spring Boot REST APIs", "Build REST APIs with Spring Boot and JPA", 24,
                false, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(30), instructor);
        courseRepository.save(course);
    }
}
