package com.example.demo.services;

import com.example.demo.models.AssessmentCategory;
import com.example.demo.payload.request.AssessmentCategoryRequest;
import com.example.demo.repository.AssessmentCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class AssessmentCategoryService {
    @Autowired
    AssessmentCategoryRepository categoryRepository;

    public ResponseEntity<List<AssessmentCategory>> getAllCategories() {
        try {
            return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), BAD_REQUEST);
    }

    public ResponseEntity<AssessmentCategory> getCategoryById(Long id) {
        try {
            return new ResponseEntity<>(categoryRepository.findByCategoryId(id).get(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new AssessmentCategory(), BAD_REQUEST);
    }

    public ResponseEntity<String> createCategory(AssessmentCategoryRequest request) {
        try {
            AssessmentCategory category = new AssessmentCategory();
            category.setName(request.name);
            category.setDescription(request.description);
            category.setWeight(request.weight);
            category.setStatus(request.status);
            categoryRepository.save(category);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", BAD_REQUEST);
    }

    public ResponseEntity<AssessmentCategory> updateCategory(Long id, AssessmentCategoryRequest request) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(request.name);
            category.setDescription(request.description);
            category.setWeight(request.weight);
            category.setStatus(request.status);
            AssessmentCategory savedCategory = categoryRepository.save(category);
            return new ResponseEntity<>(savedCategory, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> deleteCategory(Long id) {
        if (categoryRepository.existsByCategoryId(id)) {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Category not found", BAD_REQUEST);
    }
}
