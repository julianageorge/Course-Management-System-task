package com.course_management_system.cms.serviceImpl;
import org.springframework.stereotype.Service;

import com.course_management_system.cms.dto.CourseRequest;
import com.course_management_system.cms.dto.CourseResponse;
import com.course_management_system.cms.entity.Course;
import com.course_management_system.cms.repository.CourseRepository;
import com.course_management_system.cms.repository.InstructorRepository;
import com.course_management_system.cms.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.course_management_system.cms.exceptions.ResourceNotFoundException;
import com.course_management_system.cms.entity.Instructor;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    public CourseServiceImpl(CourseRepository courseRepository, InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public CourseResponse create(CourseRequest request) {
        Course course = new Course();
        applyRequest(course, request);
        course.setDeleted(false);
        return toResponse(courseRepository.save(course));
    }

    @Override
    public Page<CourseResponse> getAllCourses(Pageable pageable) {
        return courseRepository.findByDeletedFalse(pageable).map(this::toResponse);
    }

    @Override
    public CourseResponse getById(Long id) {
        return toResponse(findActiveCourse(id));
    }

    @Override
    public CourseResponse update(Long id, CourseRequest request) {
        Course course = findActiveCourse(id);
        applyRequest(course, request);
        return toResponse(courseRepository.save(course));
    }

    @Override
    public void softDelete(Long id) {
        Course course = findActiveCourse(id);
        course.setDeleted(true);
        courseRepository.save(course);
    }

    private Course findActiveCourse(Long id) {
        return courseRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
    }

    private Instructor findInstructor(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id " + id));
    }

    private void applyRequest(Course course, CourseRequest request) {
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setDurationHours(request.getDurationHours());
        course.setInstructor(findInstructor(request.getInstructorId()));
    }

    private CourseResponse toResponse(Course course) {
        Instructor instructor = course.getInstructor();
        return new CourseResponse(course.getId(), course.getTitle(), course.getDescription(), course.getDurationHours(),
                course.isDeleted(), instructor.getId(), instructor.getName());
    }
}
