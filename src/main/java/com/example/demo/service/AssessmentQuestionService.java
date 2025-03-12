package com.example.demo.service;

import com.example.demo.model.Assessment;
import com.example.demo.model.AssessmentCategory;
import com.example.demo.model.AssessmentQuestion;
import com.example.demo.payload.request.AssessmentQuestionRequest;
import com.example.demo.repository.AssessmentCategoryRepository;
import com.example.demo.repository.AssessmentQuestionRepository;
import com.example.demo.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class AssessmentQuestionService {
    @Autowired
    AssessmentCategoryRepository categoryRepository;
    @Autowired
    AssessmentQuestionRepository questionRepository;
    @Autowired
    AssessmentRepository assessmentRepository;
    public ResponseEntity<List<AssessmentQuestion>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), BAD_REQUEST);
    }

    public ResponseEntity<AssessmentQuestion> getQuestionById(Long id) {
        try {
            return new ResponseEntity<>(questionRepository.findByQuestionId(id).get(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new AssessmentQuestion(), BAD_REQUEST);
    }

    public ResponseEntity<List<AssessmentQuestion>> getQuestionsByCategory(String category) {
        try {
            Optional<AssessmentCategory> temp = categoryRepository.findByName(category);
            if (temp.isPresent()) {
                return new ResponseEntity<>(questionRepository.findByCategory(temp.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), BAD_REQUEST);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), BAD_REQUEST);
    }

    public ResponseEntity<String> createQuestion(AssessmentQuestionRequest request) {
        try {
            AssessmentQuestion question = new AssessmentQuestion();
            question.setContent(request.content);
            question.setQuestionType(request.questionType);
            question.setOptions(request.options);
            question.setAnswer(request.answer);
            question.setWeight(request.weight);
            question.setRequired(request.required);
            question.setStatus(request.status);
            Optional<AssessmentCategory> temp = categoryRepository.findByName(request.category);
            if (temp.isPresent()) {
                question.setCategory(temp.get());
            } else {
                return new ResponseEntity<>("Category not found", BAD_REQUEST);
            }
            questionRepository.save(question);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", BAD_REQUEST);
    }

    public ResponseEntity<AssessmentQuestion> updateQuestion(Long id, AssessmentQuestionRequest request) {
        return questionRepository.findById(id).map(question -> {
            question.setContent(request.content);
            question.setQuestionType(request.questionType);
            question.setOptions(request.options);
            question.setAnswer(request.answer);
            question.setWeight(request.weight);
            question.setRequired(request.required);
            question.setStatus(request.status);
            Optional<AssessmentCategory> temp = categoryRepository.findByName(request.category);
            if (temp.isPresent()) {
                question.setCategory(temp.get());
            } else {
                return new ResponseEntity<>(new AssessmentQuestion(), BAD_REQUEST);
            }
            AssessmentQuestion savedQuestion = questionRepository.save(question);
            return new ResponseEntity<>(savedQuestion, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> deleteQuestion(Long id) {
        Optional<AssessmentQuestion> questionOpt = questionRepository.findById(id);

        if (questionOpt.isEmpty()) {
            return new ResponseEntity<>("Question not found", HttpStatus.BAD_REQUEST);
        }

        AssessmentQuestion question = questionOpt.get();

        // Find all assessments containing this question
        List<Assessment> assessments = assessmentRepository.findAll();

        for (Assessment assessment : assessments) {
            if (assessment.getQuestions().contains(question)) {
                assessment.getQuestions().remove(question);
                assessmentRepository.save(assessment); // Update assessment to reflect the removal
            }
        }

        // Now safely delete the question
        questionRepository.delete(question);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
