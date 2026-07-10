package com.course_management_system.cms.serviceImpl;
import org.springframework.stereotype.Service;

import com.course_management_system.cms.repository.EnrollmentRepository;
import com.course_management_system.cms.repository.StudentRepository;
import com.course_management_system.cms.dto.EnrollmentRequest;
import com.course_management_system.cms.dto.EnrollmentResponse;
import com.course_management_system.cms.entity.Enrollment;
import com.course_management_system.cms.entity.Student;
import com.course_management_system.cms.entity.Course;
import com.course_management_system.cms.exceptions.BadRequestException;
import com.course_management_system.cms.exceptions.ResourceNotFoundException;
import com.course_management_system.cms.service.EnrollmentService;
import java.time.LocalDateTime;
import java.util.List;
import com.course_management_system.cms.repository.CourseRepository;
@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository,
            CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public EnrollmentResponse enroll(EnrollmentRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + request.getStudentId()));
        Course course = courseRepository.findByIdAndDeletedFalse(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + request.getCourseId()));

        if (enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course.getId())) {
            throw new BadRequestException("Student is already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(LocalDateTime.now());
        return toResponse(enrollmentRepository.save(enrollment));
    }

    @Override
    public List<EnrollmentResponse> getAll() {
        return enrollmentRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public EnrollmentResponse getById(Long id) {
        return toResponse(findEnrollment(id));
    }

    @Override
    public void delete(Long id) {
        enrollmentRepository.delete(findEnrollment(id));
    }

    private Enrollment findEnrollment(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id " + id));
    }

    private EnrollmentResponse toResponse(Enrollment enrollment) {
        Student student = enrollment.getStudent();
        Course course = enrollment.getCourse();
        return new EnrollmentResponse(enrollment.getId(), student.getId(), student.getName(), course.getId(),
                course.getTitle(), enrollment.getEnrolledAt());
    }
}
