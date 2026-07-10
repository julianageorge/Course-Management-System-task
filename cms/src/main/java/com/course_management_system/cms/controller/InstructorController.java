package com.course_management_system.cms.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.course_management_system.cms.service.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import com.course_management_system.cms.dto.InstructorResponse;
import com.course_management_system.cms.dto.InstructorRequest;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
@RestController
@RequestMapping("/api/instructors")
public class InstructorController {
    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @PostMapping
    public ResponseEntity<InstructorResponse> create(@RequestBody InstructorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.create(request));
    }

    @GetMapping
    public List<InstructorResponse> getAll() {
        return instructorService.getAll();
    }

    @GetMapping("/{id}")
    public InstructorResponse getById(@PathVariable Long id) {
        return instructorService.getById(id);
    }

    @PutMapping("/{id}")
    public InstructorResponse update(@PathVariable Long id, @RequestBody InstructorRequest request) {
        return instructorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        instructorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
