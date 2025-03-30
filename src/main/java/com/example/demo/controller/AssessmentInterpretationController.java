package com.example.demo.controller;

import com.example.demo.model.AssessmentInterpretation;
import com.example.demo.payload.request.AssessmentInterpretationRequest;
import com.example.demo.service.AssessmentInterpretationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test/assessment-interpretations")
@SecurityRequirement(name = "api")
public class AssessmentInterpretationController {

    @Autowired
    private AssessmentInterpretationService interpretationService;

    @GetMapping
    public ResponseEntity<List<AssessmentInterpretation>> getAllInterpretations() {
        return interpretationService.getAllInterpretations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssessmentInterpretation> getInterpretationById(@PathVariable Long id) {
        return interpretationService.getInterpretationById(id);
    }

    @PostMapping
    public ResponseEntity<String> createInterpretation(@RequestBody AssessmentInterpretationRequest interpretation) {
        return interpretationService.createInterpretation(interpretation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssessmentInterpretation> updateInterpretation(@PathVariable Long id, @RequestBody AssessmentInterpretationRequest request) {
        return interpretationService.updateInterpretation(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInterpretation(@PathVariable Long id) {
        return interpretationService.deleteInterpretation(id);
    }
}