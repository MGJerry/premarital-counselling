package com.example.demo.controller;

import com.example.demo.entity.response.ServicePackageResponse;
import com.example.demo.model.ServicePackage;
import com.example.demo.payload.request.ServicePackageRequest;
import com.example.demo.service.ServicePackageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


    @GetMapping("/extra")
    public ResponseEntity<List<ServicePackageResponse>> getAllServiceExtras() {
        return servicePackageService.getAllServiceExtras();
    }

    @GetMapping("/extra/{id}")
    public ResponseEntity<ServicePackageResponse> getServiceByIdExtra(@PathVariable Long id) {
        return servicePackageService.getServiceByIdExtra(id);
    }
    @GetMapping("/createPaymentUrl")
    public ResponseEntity<String> createPaymentUrl( ServicePackage servicePackage) {
        try{
            String paymentUrl = servicePackageService.createURLPayment(servicePackage);
            return ResponseEntity.ok(paymentUrl);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}