package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "AssessmentQuestions")
public class AssessmentQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", unique = true, nullable = false)
    private Long questionId;

    private String content;
    private String questionType;
    @ElementCollection
    private List<String> options;
    private Integer answer;
    private Double weight;
    private Boolean required;
    private String status;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private AssessmentCategory category;
}
