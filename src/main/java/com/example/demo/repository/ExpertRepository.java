package com.example.demo.repository;

import com.example.demo.entity.Expert;
import com.example.demo.enums.EStatus;
import com.example.demo.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {
    Optional<Expert> findById(Long id);

    List<Expert> findBySpecialization(Specialization specialization);

    List<Expert> findBySpecializationAndSpecializationLevelGreaterThanEqual(Specialization specialization, int specializationLevel);
}
