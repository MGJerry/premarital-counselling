package com.example.demo.repository;

import com.example.demo.model.Assessment;
import com.example.demo.model.AssessmentCategory;
import com.example.demo.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findById (Long id);
    boolean existsById (Long id);
    Optional<PasswordResetToken> findByToken (String Token);
    boolean existsByToken (String Token);
}
