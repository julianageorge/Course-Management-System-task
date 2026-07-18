package com.course_management_system.publicservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.course_management_system.publicservice.entity.Course;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findByDeletedFalse(Pageable pageable);
    
    Optional<Course> findByIdAndDeletedFalse(Long id);
    
}





