package com.course_management_system.publicservice.config;

import com.course_management_system.publicservice.entity.Student;
import com.course_management_system.publicservice.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final StudentRepository studentRepository;

    @Override
    public void run(String... args) {
        if (!studentRepository.existsByEmail("juli@gmail.com")) {
            studentRepository.save(new Student(null, "Juliana", "juli@gmail.com"));
        }
    }
}
