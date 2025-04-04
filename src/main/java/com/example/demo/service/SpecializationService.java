package com.example.demo.service;

import com.example.demo.model.AssessmentCategory;
import com.example.demo.model.Specialization;
import com.example.demo.payload.request.SpecializationRequest;
import com.example.demo.repository.AssessmentCategoryRepository;
import com.example.demo.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class SpecializationService {
    @Autowired
    SpecializationRepository specializationRepository;
    @Autowired
    AssessmentCategoryRepository categoryRepository;

    public ResponseEntity<List<Specialization>> getAllSpecializations() {
        try {
            return new ResponseEntity<>(specializationRepository.findAll(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), BAD_REQUEST);
    }

    public ResponseEntity<Specialization> getSpecializationById(Long id) {
        try {
            return new ResponseEntity<>(specializationRepository.findById(id).get(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Specialization(), BAD_REQUEST);
    }

    public ResponseEntity<String> createSpecialization(SpecializationRequest request) {
        try {
            Specialization specialization = new Specialization();
            specialization.setName(request.name);
            specialization.setDescription(request.description);
            Optional<AssessmentCategory> temp = categoryRepository.findByName(request.category);
            if (temp.isPresent()) {
                specialization.setCategory(temp.get());
            } else {
                return new ResponseEntity<>("Category not found", BAD_REQUEST);
            }
            specializationRepository.save(specialization);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", BAD_REQUEST);
    }

    public ResponseEntity<Specialization> updateSpecialization(Long id, SpecializationRequest request) {
        return specializationRepository.findById(id).map(specialization -> {
            specialization.setName(request.name);
            specialization.setDescription(request.description);
            Optional<AssessmentCategory> temp = categoryRepository.findByName(request.category);
            if (temp.isPresent()) {
                specialization.setCategory(temp.get());
            } else {
                return new ResponseEntity<>(new Specialization(), BAD_REQUEST);
            }
            Specialization savedSpecialization = specializationRepository.save(specialization);
            return new ResponseEntity<>(savedSpecialization, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> deleteSpecialization(Long id) {
        if (specializationRepository.existsById(id)) {
            specializationRepository.deleteById(id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Specialization not found", BAD_REQUEST);
    }
}
