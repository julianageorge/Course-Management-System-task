package com.course_management_system.admin.repository;

import com.course_management_system.admin.entity.Course;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findByDeletedFalse(Pageable pageable);

    Optional<Course> findByIdAndDeletedFalse(Long id);

    long countByDeletedFalse();

    long countByDeletedTrue();
}
