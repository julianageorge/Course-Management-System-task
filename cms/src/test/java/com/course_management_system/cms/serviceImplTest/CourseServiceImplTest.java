package com.course_management_system.cms.serviceImplTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.course_management_system.cms.dto.CourseRequest;
import com.course_management_system.cms.dto.CourseResponse;
import com.course_management_system.cms.entity.Course;
import com.course_management_system.cms.entity.Instructor;
import com.course_management_system.cms.exceptions.ResourceNotFoundException;
import com.course_management_system.cms.repository.CourseRepository;
import com.course_management_system.cms.repository.InstructorRepository;
import com.course_management_system.cms.serviceImpl.CourseServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private InstructorRepository instructorRepository;

    private CourseServiceImpl courseService;
    private Instructor instructor;
    private LocalDateTime registrationStart;
    private LocalDateTime registrationEnd;

    @BeforeEach
    void setUp() {
        courseService = new CourseServiceImpl(courseRepository, instructorRepository);
        instructor = new Instructor(1L, "Juliana", "juli@gmail.com", "Java");
        registrationStart = LocalDateTime.now().minusDays(1);
        registrationEnd = LocalDateTime.now().plusDays(7);
    }

    @Test
    void createSavesCourseWithInstructorAndRegistrationWindow() {
        CourseRequest request = courseRequest();
        Course savedCourse = new Course(10L, "Spring Boot", "Build REST APIs", 20, false,
                registrationStart, registrationEnd, instructor);

        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);

        CourseResponse response = courseService.create(request);

        assertEquals(10L, response.getId());
        assertEquals("Spring Boot", response.getTitle());
        assertEquals(1L, response.getInstructorId());
        assertEquals(registrationStart, response.getRegistrationStart());
        assertEquals(registrationEnd, response.getRegistrationEnd());
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void getAllReturnsOnlyActiveCoursesFromRepositoryQuery() {
        Course course = new Course(10L, "Spring Boot", "Build REST APIs", 20, false, instructor);
        PageRequest pageable = PageRequest.of(0, 10);

        when(courseRepository.findByDeletedFalse(pageable)).thenReturn(new PageImpl<>(List.of(course)));

        Page<CourseResponse> response = courseService.getAllCourses(pageable);

        assertEquals(1, response.getTotalElements());
        assertEquals("Spring Boot", response.getContent().get(0).getTitle());
    }

    @Test
    void softDeleteMarksCourseAsDeleted() {
        Course course = new Course(10L, "Spring Boot", "Build REST APIs", 20, false, instructor);

        when(courseRepository.findByIdAndDeletedFalse(10L)).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);

        courseService.softDelete(10L);

        assertEquals(true, course.isDeleted());
        verify(courseRepository).save(course);
    }

    @Test
    void getByIdThrowsWhenCourseMissingOrDeleted() {
        when(courseRepository.findByIdAndDeletedFalse(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.getById(99L));
    }

    private CourseRequest courseRequest() {
        CourseRequest request = new CourseRequest();
        request.setTitle("Spring Boot");
        request.setDescription("Build REST APIs");
        request.setDurationHours(20);
        request.setInstructorId(1L);
        request.setRegistrationStart(registrationStart);
        request.setRegistrationEnd(registrationEnd);
        return request;
    }
}
