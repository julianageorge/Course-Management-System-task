package com.course_management_system.cms.serviceImplTest;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.course_management_system.cms.dto.EnrollmentRequest;
import com.course_management_system.cms.dto.EnrollmentResponse;
import com.course_management_system.cms.entity.Course;
import com.course_management_system.cms.entity.Enrollment;
import com.course_management_system.cms.entity.Instructor;
import com.course_management_system.cms.entity.Student;
import com.course_management_system.cms.exceptions.BadRequestException;
import com.course_management_system.cms.repository.CourseRepository;
import com.course_management_system.cms.repository.EnrollmentRepository;
import com.course_management_system.cms.repository.StudentRepository;
import com.course_management_system.cms.serviceImpl.EnrollmentServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class EnrollmentServiceImplTest {
    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    private EnrollmentServiceImpl enrollmentService;
    private Student student;
    private Course course;

    @BeforeEach
    void setUp() {
        enrollmentService = new EnrollmentServiceImpl(enrollmentRepository, studentRepository, courseRepository);
        student = new Student(1L, "Juliana", "juli@example.com");
        Instructor instructor = new Instructor(2L, "George", "george@example.com", "Java");
        course = new Course(3L, "Spring Boot", "Build REST APIs", 20, false, instructor);
    }

    @Test
    void enrollCreatesEnrollmentWhenStudentIsNotAlreadyEnrolled() {
        EnrollmentRequest request = enrollmentRequest();
        Enrollment saved = new Enrollment(5L, student, course, LocalDateTime.now());

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findByIdAndDeletedFalse(3L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 3L)).thenReturn(false);
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(saved);

        EnrollmentResponse response = enrollmentService.enroll(request);

        assertEquals(5L, response.getId());
        assertEquals("Juliana", response.getStudentName());
        assertEquals("Spring Boot", response.getCourseTitle());
        verify(enrollmentRepository).save(any(Enrollment.class));
    }

    @Test
    void enrollThrowsWhenDuplicateEnrollmentExists() {
        EnrollmentRequest request = enrollmentRequest();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findByIdAndDeletedFalse(3L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 3L)).thenReturn(true);

        assertThrows(BadRequestException.class, () -> enrollmentService.enroll(request));
    }

    private EnrollmentRequest enrollmentRequest() {
        EnrollmentRequest request = new EnrollmentRequest();
        request.setStudentId(1L);
        request.setCourseId(3L);
        return request;
    }
}

