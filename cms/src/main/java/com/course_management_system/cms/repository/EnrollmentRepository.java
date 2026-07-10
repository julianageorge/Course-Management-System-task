package com.course_management_system.cms.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.course_management_system.cms.entity.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}
