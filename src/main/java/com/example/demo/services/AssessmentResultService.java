package com.example.demo.services;

import com.example.demo.models.Assessment;
import com.example.demo.models.AssessmentQuestion;
import com.example.demo.payload.request.DoAssessmentRequest;
import com.example.demo.repository.AssessmentCategoryRepository;
import com.example.demo.repository.AssessmentQuestionRepository;
import com.example.demo.repository.AssessmentRepository;
import com.example.demo.repository.AssessmentResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentResultService {
    @Autowired
    AssessmentCategoryRepository categoryRepository;
    @Autowired
    AssessmentQuestionRepository questionRepository;
    @Autowired
    AssessmentRepository assessmentRepository;
    @Autowired
    AssessmentResultRepository resultRepository;

    //todo: make the grading thingy-a-magik
    public ResponseEntity<Integer> calculateAssessmentScore(Long id, DoAssessmentRequest doAssessmentRequest) {
        Assessment assessment = assessmentRepository.findById(id).get();
        List<AssessmentQuestion> questions = assessment.getQuestions();
        int i = 0;
        int correct = 0;
        for(Integer answer : doAssessmentRequest.answers) {
            if(answer.equals(questions.get(i).getAnswer())){
                correct += 1;
            }
            i+=1;
        }
        return new ResponseEntity<>(correct, HttpStatus.OK);
    }
}
