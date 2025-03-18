package com.example.demo.controller;

import com.example.demo.model.AssessmentCategory;
import com.example.demo.model.ServicePackage;
import com.example.demo.payload.request.AssessmentCategoryRequest;
import com.example.demo.payload.request.ServicePackageRequest;
import com.example.demo.service.AssessmentCategoryService;
import com.example.demo.service.ServicePackageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test/service")
@SecurityRequirement(name = "api")
public class ServicePackageController {

    @Autowired
    private ServicePackageService servicePackageService;

    @GetMapping
    public ResponseEntity<List<ServicePackage>> getAllServices() {
        return servicePackageService.getAllServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicePackage> getServiceById(@PathVariable Long id) {
        return servicePackageService.getServiceById(id);
    }

    @PostMapping
    public ResponseEntity<String> createService(@RequestBody ServicePackageRequest service) {
        return servicePackageService.createService(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicePackage> updateService(@PathVariable Long id, @RequestBody ServicePackageRequest request) {
        return servicePackageService.updateService(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) {
        return servicePackageService.deleteService(id);
    }
}