package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "AssessmentResults")
public class AssessmentResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @ElementCollection
    private List<Integer> answers;
    private Double score;
    private Double maxScore;
    private Double scorePercentage;
    private String interpretation;
    private String recommendations;
    private String expertMatches;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime assessmentDate = LocalDateTime.now();

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    public Double getScorePercentage() {
        return scorePercentage;
    }

    public void setScorePercentage(Double scorePercentage) {
        this.scorePercentage = scorePercentage;
    }

    public String getInterpretation() {
        return interpretation;
    }

    public void setInterpretation(String interpretation) {
        this.interpretation = interpretation;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getExpertMatches() {
        return expertMatches;
    }

    public void setExpertMatches(String expertMatches) {
        this.expertMatches = expertMatches;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(LocalDateTime assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    @ManyToOne
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private AssessmentCategory category;

    public AssessmentCategory getCategory() {
        return category;
    }

    public void setCategory(AssessmentCategory category) {
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
