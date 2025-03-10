package com.example.demo.controller;

import com.example.demo.model.Assessment;
import com.example.demo.service.AssessmentService;
import com.example.demo.payload.request.AssessmentRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test/assessments")
@SecurityRequirement(name = "api")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @GetMapping
    public ResponseEntity<List<Assessment>> getAllCategories() {
        return assessmentService.getAllAssessments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assessment> getById(@PathVariable Long id) {
        return assessmentService.getAssessmentById(id);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody AssessmentRequest request) {
        return assessmentService.createAssessment(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assessment> update(@PathVariable Long id, @RequestBody AssessmentRequest request) {
        return assessmentService.updateAssessment(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return assessmentService.deleteAssessment(id);
    }
}
