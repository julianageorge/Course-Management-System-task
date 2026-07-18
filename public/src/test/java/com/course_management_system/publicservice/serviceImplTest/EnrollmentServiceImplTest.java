package com.course_management_system.publicservice.serviceImplTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.course_management_system.publicservice.dto.EnrollmentRequest;
import com.course_management_system.publicservice.dto.EnrollmentResponse;
import com.course_management_system.publicservice.entity.Course;
import com.course_management_system.publicservice.entity.Enrollment;
import com.course_management_system.publicservice.entity.Instructor;
import com.course_management_system.publicservice.entity.Student;
import com.course_management_system.publicservice.exceptions.BadRequestException;
import com.course_management_system.publicservice.exceptions.ResourceNotFoundException;
import com.course_management_system.publicservice.repository.CourseRepository;
import com.course_management_system.publicservice.repository.EnrollmentRepository;
import com.course_management_system.publicservice.repository.StudentRepository;
import com.course_management_system.publicservice.serviceImpl.EnrollmentServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        student = new Student(1L, "Juliana", "juli@gmail.com");
        Instructor instructor = new Instructor(2L, "George", "george@gmail.com", "Java");
        course = new Course(3L, "Spring Boot", "Build REST APIs", 20, false,
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1), instructor);
    }

    @Test
    void enrollCreatesEnrollmentWhenStudentIsNotAlreadyEnrolledAndRegistrationIsOpen() {
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

    @Test
    void enrollThrowsWhenRegistrationHasNotStarted() {
        EnrollmentRequest request = enrollmentRequest();
        course.setRegistrationStartTime(LocalDateTime.now().plusDays(1));
        course.setRegistrationEndTime(LocalDateTime.now().plusDays(2));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findByIdAndDeletedFalse(3L)).thenReturn(Optional.of(course));

        assertThrows(BadRequestException.class, () -> enrollmentService.enroll(request));
    }

    @Test
    void enrollThrowsWhenRegistrationIsClosed() {
        EnrollmentRequest request = enrollmentRequest();
        course.setRegistrationStartTime(LocalDateTime.now().minusDays(3));
        course.setRegistrationEndTime(LocalDateTime.now().minusDays(1));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findByIdAndDeletedFalse(3L)).thenReturn(Optional.of(course));

        assertThrows(BadRequestException.class, () -> enrollmentService.enroll(request));
    }

    @Test
    void enrollThrowsWhenStudentDoesNotExist() {
        EnrollmentRequest request = enrollmentRequest();

        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.enroll(request));
    }

    @Test
    void enrollThrowsWhenCourseDoesNotExistOrIsDeleted() {
        EnrollmentRequest request = enrollmentRequest();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findByIdAndDeletedFalse(3L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.enroll(request));
    }

    @Test
    void getAllReturnsMappedEnrollmentResponses() {
        Enrollment enrollment = new Enrollment(5L, student, course, LocalDateTime.now());

        when(enrollmentRepository.findAll()).thenReturn(List.of(enrollment));

        List<EnrollmentResponse> response = enrollmentService.getAll();

        assertEquals(1, response.size());
        assertEquals("Juliana", response.get(0).getStudentName());
    }

    @Test
    void getByIdThrowsWhenEnrollmentDoesNotExist() {
        when(enrollmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.getById(99L));
    }

    private EnrollmentRequest enrollmentRequest() {
        EnrollmentRequest request = new EnrollmentRequest();
        request.setStudentId(1L);
        request.setCourseId(3L);
        return request;
    }
}





