package com.example.demo.service;

import com.example.demo.entity.response.ServicePackageResponse;
import com.example.demo.model.ServicePackage;
import com.example.demo.payload.request.ServicePackageRequest;
import com.example.demo.repository.ServicePackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    //extra stuff
    public ResponseEntity<List<ServicePackageResponse>> getAllServiceExtras() {
        return new ResponseEntity<>(serviceRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    public ResponseEntity<ServicePackageResponse> getServiceByIdExtra(Long id) {
        try {
            return new ResponseEntity<>(mapToResponse(serviceRepository.findByPackageId(id).get()), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ServicePackageResponse(), BAD_REQUEST);
    }

    private ServicePackageResponse mapToResponse(ServicePackage servicePackage) {
        BigDecimal discountMultiplier = BigDecimal.ONE.subtract(
                servicePackage.getDiscountPercentage().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
        );
        BigDecimal total = servicePackage.getPrice().multiply(discountMultiplier);
        BigDecimal commissionShare = total.multiply(servicePackage.getCommissionFee());
        BigDecimal expertShare = total.subtract(commissionShare);

        return new ServicePackageResponse(
                servicePackage.getPackageId(),
                servicePackage.getName(),
                servicePackage.getDescription(),
                servicePackage.getSessionCount(),
                servicePackage.getValidityDays(),
                servicePackage.getPrice(),
                servicePackage.getCommissionFee(),
                servicePackage.getDiscountPercentage(),
                servicePackage.getRequirements(),
                servicePackage.getStatus(),
                total,
                commissionShare,
                expertShare
        );
    }
}
