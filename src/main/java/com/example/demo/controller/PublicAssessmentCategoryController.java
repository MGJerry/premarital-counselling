package com.example.demo.controller;

import com.example.demo.model.AssessmentCategory;
import com.example.demo.repository.AssessmentCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Public controller for accessing assessment categories without authentication
 * Used by the registration page to display category options for counselors
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/assessment-categories")
public class PublicAssessmentCategoryController {

    @Autowired
    private AssessmentCategoryRepository assessmentCategoryRepository;

    @GetMapping
    public List<AssessmentCategory> getAllCategories() {
        System.out.println("Public assessment categories endpoint called");
        return assessmentCategoryRepository.findAll();
    }
}