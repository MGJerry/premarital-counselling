package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "AssessmentInterpretations")
public class AssessmentInterpretation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double minScore; // Minimum score percentage (e.g., 0, 40, 70)

    @Column(nullable = false)
    private Double maxScore; // Maximum score percentage (e.g., 39, 69, 100)

    @Column(columnDefinition = "TEXT")
    private String interpretation;

    @Column(columnDefinition = "TEXT")
    private String recommendation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMinScore() {
        return minScore;
    }

    public void setMinScore(Double minScore) {
        this.minScore = minScore;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    public String getInterpretation() {
        return interpretation;
    }

    public void setInterpretation(String interpretation) {
        this.interpretation = interpretation;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
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

    public AssessmentInterpretation() {}

    public AssessmentInterpretation(Long id, Double minScore, Double maxScore, String interpretation, String recommendation, AssessmentCategory category) {
        this.id = id;
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.interpretation = interpretation;
        this.recommendation = recommendation;
        this.category = category;
    }
}
