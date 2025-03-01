package com.example.demo.repository;

import com.example.demo.model.Assessment;
import com.example.demo.model.AssessmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    Optional<Assessment> findByAssessmentId (Long id);
    Boolean existsByAssessmentId (Long id);
    List<Assessment> findByCategory(AssessmentCategory category);
}
