package com.course_management_system.admin.serviceImpl;

import com.course_management_system.admin.dto.EnrollmentRequest;
import com.course_management_system.admin.dto.EnrollmentResponse;
import com.course_management_system.admin.entity.Course;
import com.course_management_system.admin.entity.Enrollment;
import com.course_management_system.admin.entity.Student;
import com.course_management_system.admin.exceptions.BadRequestException;
import com.course_management_system.admin.exceptions.ResourceNotFoundException;
import com.course_management_system.admin.mapper.EnrollmentMapper;
import com.course_management_system.admin.repository.CourseRepository;
import com.course_management_system.admin.repository.EnrollmentRepository;
import com.course_management_system.admin.repository.StudentRepository;
import com.course_management_system.admin.service.EnrollmentService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public EnrollmentResponse enroll(EnrollmentRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + request.getStudentId()));
        Course course = courseRepository.findByIdAndDeletedFalse(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + request.getCourseId()));

        validateRegistrationWindow(course);

        if (enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course.getId())) {
            throw new BadRequestException("Student is already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(LocalDateTime.now());
        return EnrollmentMapper.toResponse(enrollmentRepository.save(enrollment));
    }

    @Override
    public List<EnrollmentResponse> getAll() {
        return enrollmentRepository.findAll().stream().map(EnrollmentMapper::toResponse).toList();
    }

    @Override
    public EnrollmentResponse getById(Long id) {
        return EnrollmentMapper.toResponse(findEnrollment(id));
    }

    @Override
    public void delete(Long id) {
        enrollmentRepository.delete(findEnrollment(id));
    }

    private Enrollment findEnrollment(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id " + id));
    }

    private void validateRegistrationWindow(Course course) {
        LocalDateTime now = LocalDateTime.now();
        if (course.getRegistrationStartTime() != null && now.isBefore(course.getRegistrationStartTime())) {
            throw new BadRequestException("Course registration has not started yet");
        }
        if (course.getRegistrationEndTime() != null && now.isAfter(course.getRegistrationEndTime())) {
            throw new BadRequestException("Course registration is closed");
        }
    }
}





