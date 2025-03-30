package com.example.demo.service;

import com.example.demo.model.AssessmentCategory;
import com.example.demo.model.AssessmentInterpretation;
import com.example.demo.payload.request.AssessmentInterpretationRequest;
import com.example.demo.repository.AssessmentCategoryRepository;
import com.example.demo.repository.AssessmentInterpretationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class AssessmentInterpretationService {

    @Autowired
    AssessmentInterpretationRepository interpretationRepository;

    @Autowired
    AssessmentCategoryRepository categoryRepository;
    private final Random random = new Random();
    public ResponseEntity<List<AssessmentInterpretation>> getAllInterpretations() {
        try {
            return new ResponseEntity<>(interpretationRepository.findAll(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), BAD_REQUEST);
    }

    public ResponseEntity<AssessmentInterpretation> getInterpretationById(Long id) {
        try {
            return new ResponseEntity<>(interpretationRepository.findById(id).get(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new AssessmentInterpretation(), BAD_REQUEST);
    }

    public ResponseEntity<List<AssessmentInterpretation>> getInterpretationsByCategory(String category) {
        try {
            Optional<AssessmentCategory> temp = categoryRepository.findByName(category);
            if (temp.isPresent()) {
                return new ResponseEntity<>(interpretationRepository.findByCategory(temp.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), BAD_REQUEST);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), BAD_REQUEST);
    }

    public ResponseEntity<String> createInterpretation(AssessmentInterpretationRequest request) {
        if (request.minScore > request.maxScore) {
            // return new ResponseEntity<>("Min Score is higher than Max Score. What the hell man, are you high?", BAD_REQUEST);
            return new ResponseEntity<>("Min Score is higher than Max Score", BAD_REQUEST);
        }

        try {
            AssessmentInterpretation interpretation = new AssessmentInterpretation();
            interpretation.setMinScore(request.minScore);
            interpretation.setMaxScore(request.maxScore);
            interpretation.setInterpretation(request.interpretation);
            interpretation.setRecommendation(request.recommendation);
            Optional<AssessmentCategory> temp = categoryRepository.findByName(request.category);
            if (temp.isPresent()) {
                interpretation.setCategory(temp.get());
            } else {
                return new ResponseEntity<>("Category not found", BAD_REQUEST);
            }
            interpretationRepository.save(interpretation);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", BAD_REQUEST);
    }

    public ResponseEntity<AssessmentInterpretation> updateInterpretation(Long id, AssessmentInterpretationRequest request) {
        if (request.minScore > request.maxScore) {
            // return new ResponseEntity<>("Min Score is higher than Max Score. What the hell man, are you high?", BAD_REQUEST);
            return new ResponseEntity<>(BAD_REQUEST);
        }

        return interpretationRepository.findById(id).map(interpretation -> {
            interpretation.setMinScore(request.minScore);
            interpretation.setMaxScore(request.maxScore);
            interpretation.setInterpretation(request.interpretation);
            interpretation.setRecommendation(request.recommendation);
            Optional<AssessmentCategory> temp = categoryRepository.findByName(request.category);
            if (temp.isPresent()) {
                interpretation.setCategory(temp.get());
            } else {
                return new ResponseEntity<>(new AssessmentInterpretation(), BAD_REQUEST);
            }
            AssessmentInterpretation savedInterpretation = interpretationRepository.save(interpretation);
            return new ResponseEntity<>(savedInterpretation, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> deleteInterpretation(Long id) {
        Optional<AssessmentInterpretation> interpretationOpt = interpretationRepository.findById(id);

        if (interpretationOpt.isEmpty()) {
            return new ResponseEntity<>("Interpretation not found", HttpStatus.BAD_REQUEST);
        }

        AssessmentInterpretation interpretation = interpretationOpt.get();

        // Now safely delete the interpretation
        interpretationRepository.delete(interpretation);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    public Optional<AssessmentInterpretation> getInterpretation(String category, Double scorePercentage) {
        // check if category exist
        Optional<AssessmentCategory> temp = categoryRepository.findByName(category);
        if (!temp.isPresent()) {
            throw new RuntimeException("Category " + category + " doesn't exist");
        }

        List<AssessmentInterpretation> interpretations = interpretationRepository.findByCategoryAndScoreRange(category, scorePercentage);

        if (interpretations.isEmpty()) {
            return Optional.empty();
        }

        // Select a random interpretation from the list if multiple exist
        return Optional.of(interpretations.get(random.nextInt(interpretations.size())));
    }

}
