package com.example.demo.service;

import com.example.demo.entity.Expert;
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
import java.util.*;

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
    SpecializationRepository specializationRepository;
    @Autowired
    AssessmentResultRepository resultRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ExpertRepository expertRepository;
    @Autowired
    AssessmentInterpretationService interpretationService;

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
        Map<String, Double> scoreResult = calculateAssessmentScore(id, answers);
        result.setScore(scoreResult.get("score"));
        result.setMaxScore(scoreResult.get("maxPossibleScore"));
        result.setScorePercentage(scoreResult.get("scorePercentage"));

        //random recommendations
        Optional<AssessmentInterpretation> interpretationOpt = interpretationService.getInterpretation(assessment.get().getCategory().getName(), scoreResult.get("scorePercentage"));
        result.setInterpretation(interpretationOpt.get().getInterpretation());
        result.setRecommendations(interpretationOpt.get().getRecommendation());

        //result.setExpertMatches(getRandomExpert());
        result.setExpertMatches(getGoodExpert(assessment.get().getCategory(), scoreResult.get("scorePercentage")));

        result.setCreatedAt(LocalDateTime.now());
        result.setAssessmentDate(LocalDateTime.now());

        resultRepository.save(result);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    //todo: make the grading thingy-a-magik
    public Map<String, Double> calculateAssessmentScore(Long id, List<Integer> answers) {
        double score = 0.0;
        double maxPossibleScore = 0.0;

        if (answers.isEmpty()) {
            return Map.of("score", 0.0, "scorePercentage", 0.0);
        }

        try {
            Optional<Assessment> assessmentOpt = assessmentRepository.findById(id);
            if (assessmentOpt.isEmpty()) {
                return Map.of("score", 0.0, "scorePercentage", 0.0);
            }

            Assessment assessment = assessmentOpt.get();
            List<AssessmentQuestion> questions = assessment.getQuestions();

            for (int i = 0; i < questions.size(); i++) {
                AssessmentQuestion question = questions.get(i);
                Map<String, Double> options = question.getOptions(); // Option weights

                if (i < answers.size()) {
                    int selectedIndex = answers.get(i); // Selected option index
                    List<String> optionKeys = new ArrayList<>(options.keySet()); // Convert map keys to list

                    if (selectedIndex >= 0 && selectedIndex < optionKeys.size()) {
                        String selectedOption = optionKeys.get(selectedIndex);
                        score += options.getOrDefault(selectedOption, 0.0);

                        //debug
                        // System.out.println("Choice: " + selectedIndex + ", Option Text: " + selectedOption + ", Option Weight: " + options.getOrDefault(selectedOption, 0.0));
                    }
                }

                // Calculate max possible score (assuming highest-weighted option per question)
                maxPossibleScore += options.values().stream().max(Double::compare).orElse(0.0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        double scorePercentage = (maxPossibleScore > 0) ? (score / maxPossibleScore) * 100 : 0.0;
        return Map.of("score", score, "scorePercentage", scorePercentage, "maxPossibleScore", maxPossibleScore);
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

    private final Random random = new Random();

    private <T> T getRandomElement(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    private String getRandomExpert() {
        List<Expert> experts = expertRepository.findAll();
        if (experts.isEmpty()) {
            //placeholder, in case of no experts
            return "Nguyen Van A";
        }
        return experts.get(random.nextInt(experts.size())).getFullName();
    }

    private String getGoodExpert(AssessmentCategory category, Double scorePercentage) {
        List<Specialization> specializations = specializationRepository.findByCategory(category);

        if (specializations.isEmpty()) {
            throw new RuntimeException("No specialization found. If this occurs, please report to System Admin");
        }

        // problem severity
        int specializationLevel = 1; // default value, to prevent errors
        if (scorePercentage >= 70) {
            // do nothing
            specializationLevel = 1;
        } else if (scorePercentage >= 40) {
            specializationLevel = 2;
        } else {
            specializationLevel = 3;
        }

        List<Expert> experts = new ArrayList<>();
        for (Specialization specialization : specializations) {
            List<Expert> tmpEx = expertRepository.findBySpecializationAndSpecializationLevelGreaterThanEqual(specialization, specializationLevel);

            if (tmpEx.isEmpty()) {
                // no experts with the required specializationLevel or higher
                experts.addAll(expertRepository.findBySpecialization(specialization));
            } else {
                experts.addAll(tmpEx);
            }
        }

        if (experts.isEmpty()) {
            //placeholder, in case of no experts
            return "Nguyen Van A";
        }
        return experts.get(random.nextInt(experts.size())).getFullName();
    }
}
