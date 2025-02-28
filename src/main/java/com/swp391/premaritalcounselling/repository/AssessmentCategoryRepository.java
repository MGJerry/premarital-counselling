package com.swp391.premaritalcounselling.repository;

import com.swp391.premaritalcounselling.models.AssessmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssessmentCategoryRepository extends JpaRepository<AssessmentCategory, Long> {
    Optional<AssessmentCategory> findByCategoryId (Long id);
    Boolean existsByCategoryId (Long id);
    Optional<AssessmentCategory> findByName (String name);
}
