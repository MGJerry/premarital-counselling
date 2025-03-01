package com.example.demo.controller;

import com.example.demo.model.AssessmentCategory;
import com.example.demo.service.AssessmentCategoryService;
import com.example.demo.payload.request.AssessmentCategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test/assessment-categories")
public class AssessmentCategoryController {

    @Autowired
    private AssessmentCategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<AssessmentCategory>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssessmentCategory> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody AssessmentCategoryRequest category) {
        return categoryService.createCategory(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssessmentCategory> updateCategory(@PathVariable Long id, @RequestBody AssessmentCategoryRequest request) {
        return categoryService.updateCategory(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}