package com.course_management_system.admin.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.course_management_system.admin.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    
}





