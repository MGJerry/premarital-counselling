package com.example.demo.controller;

import com.example.demo.model.AssessmentQuestion;
import com.example.demo.service.AssessmentQuestionService;
import com.example.demo.payload.request.AssessmentQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test/assessment-questions")
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
    public ResponseEntity<String> createQuestion(@RequestBody AssessmentQuestionRequest category) {
        return questionService.createQuestion(category);
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
