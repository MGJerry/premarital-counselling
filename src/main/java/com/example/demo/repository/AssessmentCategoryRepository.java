package com.example.demo.repository;

import com.example.demo.models.AssessmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssessmentCategoryRepository extends JpaRepository<AssessmentCategory, Long> {
    Optional<AssessmentCategory> findByCategoryId (Long id);
    Boolean existsByCategoryId (Long id);
    Optional<AssessmentCategory> findByName (String name);
}
