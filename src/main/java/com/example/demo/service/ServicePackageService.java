package com.example.demo.service;

import com.example.demo.model.ServicePackage;
import com.example.demo.payload.request.ServicePackageRequest;
import com.example.demo.repository.ServicePackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class ServicePackageService {
    @Autowired
    ServicePackageRepository serviceRepository;

    public ResponseEntity<List<ServicePackage>> getAllServices() {
        try {
            return new ResponseEntity<>(serviceRepository.findAll(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), BAD_REQUEST);
    }

    public ResponseEntity<ServicePackage> getServiceById(Long id) {
        try {
            return new ResponseEntity<>(serviceRepository.findByPackageId(id).get(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ServicePackage(), BAD_REQUEST);
    }

    public ResponseEntity<String> createService(ServicePackageRequest request) {
        try {
            ServicePackage service = new ServicePackage();
            service.setName(request.name);
            service.setDescription(request.description);
            service.setSessionCount(request.sessionCount);
            service.setValidityDays(request.validityDays);
            service.setPrice(new BigDecimal(request.price));
            service.setCommissionFee(new BigDecimal(request.commissionFee));
            service.setDiscountPercentage(new BigDecimal(request.discountPercentage));
            service.setStatus(request.status);
            serviceRepository.save(service);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", BAD_REQUEST);
    }

    public ResponseEntity<ServicePackage> updateService(Long id, ServicePackageRequest request) {
        return serviceRepository.findById(id).map(service -> {
            service.setName(request.name);
            service.setDescription(request.description);
            service.setSessionCount(request.sessionCount);
            service.setValidityDays(request.validityDays);
            service.setPrice(new BigDecimal(request.price));
            service.setCommissionFee(new BigDecimal(request.commissionFee));
            service.setDiscountPercentage(new BigDecimal(request.discountPercentage));
            service.setStatus(request.status);
            ServicePackage savedCategory = serviceRepository.save(service);
            return new ResponseEntity<>(savedCategory, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> deleteService(Long id) {
        if (serviceRepository.existsByPackageId(id)) {
            serviceRepository.deleteById(id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Service not found", BAD_REQUEST);
    }
}
