package com.course_management_system.publicservice.serviceImpl;

import com.course_management_system.publicservice.dto.CourseRequest;
import com.course_management_system.publicservice.dto.CourseResponse;
import com.course_management_system.publicservice.entity.Course;
import com.course_management_system.publicservice.entity.Instructor;
import com.course_management_system.publicservice.exceptions.ResourceNotFoundException;
import com.course_management_system.publicservice.mapper.CourseMapper;
import com.course_management_system.publicservice.repository.CourseRepository;
import com.course_management_system.publicservice.repository.InstructorRepository;
import com.course_management_system.publicservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    @Override
    public CourseResponse create(CourseRequest request) {
        Course course = new Course();
        CourseMapper.updateEntity(course, request, findInstructor(request.getInstructorId()));
        course.setDeleted(false);
        return CourseMapper.toResponse(courseRepository.save(course));
    }

    @Override
    public Page<CourseResponse> getAllCourses(Pageable pageable) {
        return courseRepository.findByDeletedFalse(pageable).map(CourseMapper::toResponse);
    }

    @Override
    public CourseResponse getById(Long id) {
        return CourseMapper.toResponse(findActiveCourse(id));
    }

    @Override
    public CourseResponse update(Long id, CourseRequest request) {
        Course course = findActiveCourse(id);
        CourseMapper.updateEntity(course, request, findInstructor(request.getInstructorId()));
        return CourseMapper.toResponse(courseRepository.save(course));
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
}





