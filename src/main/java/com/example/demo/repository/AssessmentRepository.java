package com.example.demo.repository;

import com.example.demo.models.Assessment;
import com.example.demo.models.AssessmentCategory;
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
