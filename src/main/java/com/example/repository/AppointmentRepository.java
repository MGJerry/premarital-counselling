package com.example.repository;

import com.example.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByMemberId(Long memberId);
    List<Appointment> findByExpertId(Long expertId);
    List<Appointment> findByMemberIdAndStatus(Long memberId, String status);
    List<Appointment> findByExpertIdAndStatus(Long expertId, String status);
} 