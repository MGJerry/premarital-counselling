package com.example.demo.controller;

import com.example.demo.model.Specialization;
import com.example.demo.payload.request.SpecializationRequest;
import com.example.demo.service.SpecializationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test/specializations")
@SecurityRequirement(name = "api")
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;

    @GetMapping
    public ResponseEntity<List<Specialization>> getAllSpecializations() {
        return specializationService.getAllSpecializations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specialization> getById(@PathVariable Long id) {
        return specializationService.getSpecializationById(id);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody SpecializationRequest request) {
        return specializationService.createSpecialization(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Specialization> update(@PathVariable Long id, @RequestBody SpecializationRequest request) {
        return specializationService.updateSpecialization(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return specializationService.deleteSpecialization(id);
    }
}
