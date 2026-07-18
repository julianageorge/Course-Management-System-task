package com.course_management_system.admin.repository;

import com.course_management_system.admin.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    boolean existsByEmail(String email);
}
