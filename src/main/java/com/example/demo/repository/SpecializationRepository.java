package com.example.demo.repository;

import com.example.demo.model.AssessmentCategory;
import com.example.demo.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    List<Specialization> findByCategory(AssessmentCategory category);
}
