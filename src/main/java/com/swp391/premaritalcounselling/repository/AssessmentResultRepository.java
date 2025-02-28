package com.swp391.premaritalcounselling.repository;

import com.swp391.premaritalcounselling.models.Assessment;
import com.swp391.premaritalcounselling.models.AssessmentCategory;
import com.swp391.premaritalcounselling.models.AssessmentResult;
import com.swp391.premaritalcounselling.models.Member;
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
