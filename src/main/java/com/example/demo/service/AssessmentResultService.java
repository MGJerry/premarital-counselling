package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.model.*;
import com.example.demo.payload.request.DoAssessmentRequest;
import com.example.demo.payload.request.UpdateAssessmentResultRequest;
import com.example.demo.repository.*;
import com.example.demo.util.AuthenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MemberRepository memberRepository;

    public ResponseEntity<List<AssessmentResult>> getAllResults() {
        try {
            return new ResponseEntity<>(resultRepository.findAll(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), BAD_REQUEST);
    }

    public ResponseEntity<AssessmentResult> getResultById(Long id) {
        try {
            return new ResponseEntity<>(resultRepository.findByResultId(id).get(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new AssessmentResult(), BAD_REQUEST);
    }

    public ResponseEntity<AssessmentResult> getResultByUsername(String username) {
        try {
            User user = userRepository.findByFullName(username).orElseThrow();
            Member member = memberRepository.findByUser(user).orElseThrow();
            return new ResponseEntity<>(resultRepository.findByMember(member).orElseThrow(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new AssessmentResult(), BAD_REQUEST);
    }

    public ResponseEntity<String> doAssessment(DoAssessmentRequest doAssessmentRequest) {
        try {
            Member member = memberRepository.findByUser(AuthenUtil.getAuthenticatedUser()).orElseThrow();

            return createResult(doAssessmentRequest.id, doAssessmentRequest.answers, member);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Submit Answer Failed", BAD_REQUEST);
    }

    public ResponseEntity<String> createResult(Long id, List<Integer> answers, Member member) {
        AssessmentResult result = new AssessmentResult();

        result.setAnswers(answers);
        Optional<Assessment> assessment = assessmentRepository.findById(id);
        if (assessment.isPresent()) {
            result.setAssessment(assessment.get());
            result.setCategory(assessment.get().getCategory());
        } else {
            return new ResponseEntity<>("Assessment with id " + id + " doesn't exist", BAD_REQUEST);
        }
        result.setMember(member);

        //grading
        result.setScore(calculateAssessmentScore(id, answers));

        result.setCreatedAt(LocalDateTime.now());
        result.setAssessmentDate(LocalDateTime.now());

        resultRepository.save(result);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    //todo: make the grading thingy-a-magik
    public Double calculateAssessmentScore(Long id, List<Integer> answers) {
        int correct = 0;
        Double score = 0.0;
        if (answers.isEmpty()) {
            //return correct;
            return score;
        }
        try {
            Assessment assessment = assessmentRepository.findById(id).get();
            List<AssessmentQuestion> questions = assessment.getQuestions();
            int i = 0;
            for(Integer answer : answers) {
                if(answer.equals(questions.get(i).getAnswer())){
                    correct += 1;
                    score += questions.get(i).getWeight();
                }
                i+=1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return correct;
        return score;
    }

    public ResponseEntity<AssessmentResult> updateResult(Long id, UpdateAssessmentResultRequest request) {
        return resultRepository.findById(id).map(result -> {
            result.setInterpretation(request.interpretation);
            result.setRecommendations(request.recommendations);
            result.setExpertMatches(request.expertMatches);
            result.setAssessmentDate(LocalDateTime.now());
            AssessmentResult savedResult = resultRepository.save(result);
            return new ResponseEntity<>(savedResult, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> deleteResult(Long id) {
        if (resultRepository.existsByResultId(id)) {
            resultRepository.deleteById(id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Result not found", BAD_REQUEST);
    }
}
