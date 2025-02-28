package com.swp391.premaritalcounselling.services;

import com.swp391.premaritalcounselling.models.Assessment;
import com.swp391.premaritalcounselling.models.AssessmentQuestion;
import com.swp391.premaritalcounselling.payload.request.DoAssessmentRequest;
import com.swp391.premaritalcounselling.repository.AssessmentCategoryRepository;
import com.swp391.premaritalcounselling.repository.AssessmentQuestionRepository;
import com.swp391.premaritalcounselling.repository.AssessmentRepository;
import com.swp391.premaritalcounselling.repository.AssessmentResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
