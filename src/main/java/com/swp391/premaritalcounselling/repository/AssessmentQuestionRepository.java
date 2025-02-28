package com.swp391.premaritalcounselling.repository;

import com.swp391.premaritalcounselling.models.AssessmentCategory;
import com.swp391.premaritalcounselling.models.AssessmentQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentQuestionRepository extends JpaRepository<AssessmentQuestion, Long> {
    Optional<AssessmentQuestion> findByQuestionId (Long id);
    Boolean existsByQuestionId (Long id);
    List<AssessmentQuestion> findByCategory(AssessmentCategory category);
}
