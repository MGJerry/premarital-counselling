package com.example.demo.controllers;

import com.example.demo.models.Assessment;
import com.example.demo.services.AssessmentService;
import com.example.demo.payload.request.AssessmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test/assessments")
public class AssessmentController {

    @Autowired
    private AssessmentService questionService;

    @GetMapping
    public ResponseEntity<List<Assessment>> getAllCategories() {
        return questionService.getAllAssessments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assessment> getById(@PathVariable Long id) {
        return questionService.getAssessmentById(id);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody AssessmentRequest category) {
        return questionService.createAssessment(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assessment> update(@PathVariable Long id, @RequestBody AssessmentRequest request) {
        return questionService.updateAssessment(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return questionService.deleteAssessment(id);
    }
}
