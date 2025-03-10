package com.example.demo.controller;

import com.example.demo.model.AssessmentResult;
import com.example.demo.payload.request.DoAssessmentRequest;
import com.example.demo.payload.request.UpdateAssessmentResultRequest;
import com.example.demo.service.AssessmentResultService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test/assessment-results")
@SecurityRequirement(name = "api")
public class AssessmentResultController {

    @Autowired
    private AssessmentResultService resultService;

    @GetMapping
    public ResponseEntity<List<AssessmentResult>> getAllCategories() {
        return resultService.getAllResults();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssessmentResult> getById(@PathVariable Long id) {
        return resultService.getResultById(id);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<AssessmentResult> getById(@PathVariable String username) {
        return resultService.getResultByUsername(username);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody DoAssessmentRequest request) {
        return resultService.doAssessment(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssessmentResult> update(@PathVariable Long id, @RequestBody UpdateAssessmentResultRequest request) {
        return resultService.updateResult(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return resultService.deleteResult(id);
    }
}
