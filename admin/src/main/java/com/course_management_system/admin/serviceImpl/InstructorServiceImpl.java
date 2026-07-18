package com.course_management_system.admin.serviceImpl;

import com.course_management_system.admin.dto.InstructorRequest;
import com.course_management_system.admin.dto.InstructorResponse;
import com.course_management_system.admin.entity.Instructor;
import com.course_management_system.admin.exceptions.ResourceNotFoundException;
import com.course_management_system.admin.mapper.InstructorMapper;
import com.course_management_system.admin.repository.InstructorRepository;
import com.course_management_system.admin.service.InstructorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepository instructorRepository;

    @Override
    public InstructorResponse create(InstructorRequest request) {
        Instructor instructor = new Instructor();
        InstructorMapper.updateEntity(instructor, request);
        return InstructorMapper.toResponse(instructorRepository.save(instructor));
    }

    @Override
    public List<InstructorResponse> getAll() {
        return instructorRepository.findAll().stream().map(InstructorMapper::toResponse).toList();
    }

    @Override
    public InstructorResponse getById(Long id) {
        return InstructorMapper.toResponse(findInstructor(id));
    }

    @Override
    public InstructorResponse update(Long id, InstructorRequest request) {
        Instructor instructor = findInstructor(id);
        InstructorMapper.updateEntity(instructor, request);
        return InstructorMapper.toResponse(instructorRepository.save(instructor));
    }

    @Override
    public void delete(Long id) {
        instructorRepository.delete(findInstructor(id));
    }

    private Instructor findInstructor(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id " + id));
    }
}





