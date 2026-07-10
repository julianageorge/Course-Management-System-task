package com.course_management_system.cms.serviceImpl;
import org.springframework.stereotype.Service;
import com.course_management_system.cms.dto.InstructorRequest;
import com.course_management_system.cms.dto.InstructorResponse;
import com.course_management_system.cms.entity.Instructor;
import com.course_management_system.cms.repository.InstructorRepository;
import com.course_management_system.cms.service.InstructorService;
import com.course_management_system.cms.exceptions.ResourceNotFoundException;
import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepository instructorRepository;

    public InstructorServiceImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public InstructorResponse create(InstructorRequest request) {
        Instructor instructor = new Instructor();
        applyRequest(instructor, request);
        return toResponse(instructorRepository.save(instructor));
    }

    @Override
    public List<InstructorResponse> getAll() {
        return instructorRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public InstructorResponse getById(Long id) {
        return toResponse(findInstructor(id));
    }

    @Override
    public InstructorResponse update(Long id, InstructorRequest request) {
        Instructor instructor = findInstructor(id);
        applyRequest(instructor, request);
        return toResponse(instructorRepository.save(instructor));
    }

    @Override
    public void delete(Long id) {
        instructorRepository.delete(findInstructor(id));
    }

    private Instructor findInstructor(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id " + id));
    }

    private void applyRequest(Instructor instructor, InstructorRequest request) {
        instructor.setName(request.getName());
        instructor.setEmail(request.getEmail());
        instructor.setExpertise(request.getExpertise());
    }

    private InstructorResponse toResponse(Instructor instructor) {
        return new InstructorResponse(instructor.getId(), instructor.getName(), instructor.getEmail(),
                instructor.getExpertise());
    }
}
