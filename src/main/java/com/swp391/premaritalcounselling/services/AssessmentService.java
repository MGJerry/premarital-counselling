package com.swp391.premaritalcounselling.services;

import com.swp391.premaritalcounselling.models.Assessment;
import com.swp391.premaritalcounselling.models.AssessmentCategory;
import com.swp391.premaritalcounselling.models.AssessmentQuestion;
import com.swp391.premaritalcounselling.payload.request.AssessmentQuestionRequest;
import com.swp391.premaritalcounselling.payload.request.AssessmentRequest;
import com.swp391.premaritalcounselling.repository.AssessmentCategoryRepository;
import com.swp391.premaritalcounselling.repository.AssessmentQuestionRepository;
import com.swp391.premaritalcounselling.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class AssessmentService {
    @Autowired
    AssessmentCategoryRepository categoryRepository;
    @Autowired
    AssessmentQuestionRepository questionRepository;
    @Autowired
    AssessmentRepository assessmentRepository;

    public ResponseEntity<List<Assessment>> getAllAssessments() {
        try {
            return new ResponseEntity<>(assessmentRepository.findAll(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), BAD_REQUEST);
    }

    public ResponseEntity<Assessment> getAssessmentById(Long id) {
        try {
            return new ResponseEntity<>(assessmentRepository.findByAssessmentId(id).get(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Assessment(), BAD_REQUEST);
    }

    public ResponseEntity<String> createAssessment(AssessmentRequest request) {
        List<AssessmentQuestion> questions = new ArrayList<>();
        for (AssessmentQuestionRequest question : request.questions) {
            AssessmentQuestion tempQuestion = new AssessmentQuestion();
            tempQuestion.setContent(question.content);
            tempQuestion.setQuestionType(question.questionType);
            tempQuestion.setOptions(question.options);
            tempQuestion.setAnswer(question.answer);
            tempQuestion.setWeight(question.weight);
            tempQuestion.setRequired(question.required);
            tempQuestion.setStatus(question.status);
            Optional<AssessmentCategory> temp = categoryRepository.findByName(question.category);
            if (temp.isPresent()) {
                tempQuestion.setCategory(temp.get());
            } else {
                return new ResponseEntity<>("Category " + question.category + " doesn't exist", BAD_REQUEST);
            }
            AssessmentQuestion savedQuestion = questionRepository.save(tempQuestion);
            questions.add(savedQuestion);
        }
        Assessment assessment = new Assessment();
        assessment.setQuestions(questions);
        Optional<AssessmentCategory> assessmentCategory = categoryRepository.findByName(request.category);
        if (assessmentCategory.isPresent()) {
            assessment.setCategory(assessmentCategory.get());
        } else {
            return new ResponseEntity<>("Category " + request.category + " doesn't exist", BAD_REQUEST);
        }

        assessmentRepository.save(assessment);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<AssessmentQuestion> updateAssessment(Long id, AssessmentRequest request) {
        return assessmentRepository.findById(id).map(assessment -> {
            List<AssessmentQuestion> questions = assessment.getQuestions();
            for (AssessmentQuestionRequest question : request.questions) {
                AssessmentQuestion tempQuestion;
                if (question.id != null & question.id != 0) {
                     tempQuestion = questionRepository.findByQuestionId(question.id).get();
                } else {
                     tempQuestion = new AssessmentQuestion();
                }
                tempQuestion.setContent(question.content);
                tempQuestion.setQuestionType(question.questionType);
                tempQuestion.setOptions(question.options);
                tempQuestion.setAnswer(question.answer);
                tempQuestion.setWeight(question.weight);
                tempQuestion.setRequired(question.required);
                tempQuestion.setStatus(question.status);
                Optional<AssessmentCategory> temp = categoryRepository.findByName(question.category);
                if (temp.isPresent()) {
                    tempQuestion.setCategory(temp.get());
                } else {
                    return new ResponseEntity<>(new Assessment(), BAD_REQUEST);
                }
                if (question.id == null || question.id == 0) {
                    AssessmentQuestion savedQuestion = questionRepository.save(tempQuestion);
                    questions.add(savedQuestion);
                }
            }
            Optional<AssessmentCategory> temp = categoryRepository.findByName(request.category);
            if (temp.isPresent()) {
                assessment.setCategory(temp.get());
            } else {
                return new ResponseEntity<>(new Assessment(), BAD_REQUEST);
            }
            Assessment savedAssessment = assessmentRepository.save(assessment);
            return new ResponseEntity<>(savedAssessment, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> deleteAssessment(Long id) {
        if (assessmentRepository.existsByAssessmentId(id)) {
            assessmentRepository.deleteById(id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Assessment not found", BAD_REQUEST);
    }
}
