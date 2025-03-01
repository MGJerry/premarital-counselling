package com.example.demo.repository;

import com.example.demo.models.Assessment;
import com.example.demo.models.AssessmentCategory;
import com.example.demo.models.AssessmentResult;
import com.example.demo.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {
    Optional<AssessmentResult> findByResultId (Long id);
    Boolean existsByResultId (Long id);
    Optional<AssessmentResult> findByAssessmentAndMember (Assessment assessment, Member member);
    List<AssessmentResult> findByCategory(AssessmentCategory category);
}
