package com.course_management_system.publicservice.repository;

import com.course_management_system.publicservice.entity.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);

    Optional<Student> findByEmail(String email);
}
