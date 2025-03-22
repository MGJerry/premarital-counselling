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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    @Autowired
    private ExpertRepository expertRepository;

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

        //random recommendations
        result.setInterpretation(getRandomElement(INTERPRETATIONS));
        result.setRecommendations(getRandomElement(RECOMMENDATIONS));
        result.setExpertMatches(getRandomExpert());

        result.setCreatedAt(LocalDateTime.now());
        result.setAssessmentDate(LocalDateTime.now());

        resultRepository.save(result);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    //todo: make the grading thingy-a-magik
    public Double calculateAssessmentScore(Long id, List<Integer> answers) {
        Double score = 0.0;
        if (answers.isEmpty()) {
            //return correct;
            return score;
        }
        try {
            Assessment assessment = assessmentRepository.findById(id).get();
            List<AssessmentQuestion> questions = assessment.getQuestions();

            for (int i = 0; i < questions.size(); i++) {
                AssessmentQuestion question = questions.get(i);

                if (i < answers.size()) {
                    int selectedIndex = answers.get(i); // Selected option index

                    List<String> optionKeys = question.getOptions().keySet().stream().toList(); // Get options as a list

                    if (selectedIndex >= 0 && selectedIndex < optionKeys.size()) {
                        String selectedOption = optionKeys.get(selectedIndex); // Get option text
                        Double optionWeight = question.getOptions().getOrDefault(selectedOption, 0.0);

                        score += optionWeight;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private static final List<String> INTERPRETATIONS = List.of(
            "You and your partner have a strong emotional connection, but exploring each other's love languages can deepen your bond.",
            "Your conflict resolution strategies are solid, but practicing patience during disagreements will strengthen your relationship.",
            "Your financial habits are aligned well, but discussing long-term investments can bring more stability.",
            "You share similar values, which is a strong foundation for marriage. Continue open communication to maintain harmony.",
            "Your relationship thrives on trust and understanding. Keep nurturing it with quality time and shared experiences.",
            "Some areas of your relationship need improvement. Consider engaging in guided discussions on important topics like finances and personal growth.",
            "Your partner feels appreciated, but showing gratitude more frequently can boost your relationship satisfaction.",
            "Balancing personal goals and couple goals is key to a long-lasting relationship. Ensure both are aligned.",
            "Your conflict resolution style is healthy, but focusing on proactive communication can help avoid unnecessary misunderstandings.",
            "Your responses indicate strong relationship resilience. Keep working together to maintain balance and mutual respect."
    );

    private static final List<String> RECOMMENDATIONS = List.of(
            "Schedule weekly relationship check-ins to ensure both partners feel heard and valued.",
            "Try the 'Five Love Languages' test together to understand how to express affection more effectively.",
            "Create a financial plan together, setting clear goals for savings, expenses, and investments.",
            "Engage in active listening exercises to enhance communication skills.",
            "Attend a couples' workshop or retreat to strengthen your bond and learn new relationship skills.",
            "Make date nights a priority to keep the romance alive amidst daily responsibilities.",
            "Read a relationship book together and discuss insights that resonate with both of you.",
            "Practice expressing appreciation daily to reinforce mutual respect and affection.",
            "Work on conflict resolution by setting 'cool-down' periods before discussing serious disagreements.",
            "Develop shared rituals like morning coffee together or bedtime conversations to stay emotionally connected.",
            "Identify common hobbies to enjoy together and strengthen your connection through shared activities.",
            "Practice gratitude journaling, noting things you appreciate about each other every day.",
            "Seek professional counseling if certain issues seem unresolved despite your efforts.",
            "Establish personal and couple goals to ensure both partners grow individually and together.",
            "Use 'I' statements during arguments to prevent blame and encourage open dialogue."
    );

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
}
