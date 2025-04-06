package com.example.demo.controller;

import com.example.demo.model.Specialization;
import com.example.demo.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Public controller for accessing specializations without authentication
 * Used by the registration page to display specialization options for counselors
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/specializations")
public class PublicSpecializationController {

    @Autowired
    private SpecializationRepository specializationRepository;

    @GetMapping
    public List<Specialization> getAllSpecializations() {
        System.out.println("Public specializations endpoint called");
        return specializationRepository.findAll();
    }
}