package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "AssessmentResults")
public class AssessmentResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @ElementCollection
    private List<Integer> answers;
    private Double score;
    private String interpretation;
    private String recommendations;
    private String expertMatches;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime assessmentDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private AssessmentCategory category;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
