package com.example.demo.repository;

import com.example.demo.model.AssessmentCategory;
import com.example.demo.model.AssessmentInterpretation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentInterpretationRepository extends JpaRepository<AssessmentInterpretation, Long> {
    List<AssessmentInterpretation> findByCategory(AssessmentCategory category);

    @Query("SELECT q FROM AssessmentInterpretation q WHERE q.category.name = :category AND :scorePercentage BETWEEN q.minScore AND q.maxScore")
    List<AssessmentInterpretation> findByCategoryAndScoreRange(String category, Double scorePercentage);
}
