package com.course_management_system.cms.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.course_management_system.cms.entity.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
