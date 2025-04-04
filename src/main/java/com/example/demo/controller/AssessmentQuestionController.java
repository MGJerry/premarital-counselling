package com.example.demo.controller;

import com.example.demo.model.AssessmentQuestion;
import com.example.demo.service.AssessmentQuestionService;
import com.example.demo.payload.request.AssessmentQuestionRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test/assessment-questions")
@SecurityRequirement(name = "api")
public class AssessmentQuestionController {

    @Autowired
    private AssessmentQuestionService questionService;

    @GetMapping
    public ResponseEntity<List<AssessmentQuestion>> getAllCategories() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssessmentQuestion> getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    @PostMapping
    public ResponseEntity<String> createQuestion(@RequestBody AssessmentQuestionRequest request) {
        return questionService.createQuestion(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssessmentQuestion> updateQuestion(@PathVariable Long id, @RequestBody AssessmentQuestionRequest request) {
        return questionService.updateQuestion(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
        return questionService.deleteQuestion(id);
    }
}
