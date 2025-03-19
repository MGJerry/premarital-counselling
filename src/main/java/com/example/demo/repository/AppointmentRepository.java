package com.example.demo.repository;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Expert;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findById(Long id);
    List<Appointment> findByMember(User member);
    List<Appointment> findByExpert(Expert expert);
    List<Appointment> findByStatus(boolean status);
    List<Appointment> findByAppointmentDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Appointment> findByMemberAndStatus(User member, boolean status);
    List<Appointment> findByExpertAndStatus(Expert expert, boolean status);
} 