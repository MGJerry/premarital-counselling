package com.example.demo.repository;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Expert;
import com.example.demo.entity.Feedback;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    // Find feedback by appointment
    Optional<Feedback> findByAppointmentId(Long appointmentId);
    
    // Find all feedback for an expert
    @Query("SELECT f FROM Feedback f WHERE f.appointment.expert.id = :expertId")
    List<Feedback> findAllByExpertId(@Param("expertId") Long expertId);
    
    // Find all feedback given by a member
    @Query("SELECT f FROM Feedback f WHERE f.appointment.member.id = :memberId")
    List<Feedback> findAllByMemberId(@Param("memberId") Long memberId);
    
    // Calculate average rating for an expert
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.appointment.expert.id = :expertId")
    Double getAverageRatingForExpert(@Param("expertId") Long expertId);
} 