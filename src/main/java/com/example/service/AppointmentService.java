package com.example.service;

import com.example.model.Appointment;
import com.example.repository.AppointmentRepository;
import com.example.payload.request.AppointmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Transactional
    public Appointment createAppointment(AppointmentRequest request, Long memberId) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDateTime(request.getAppointmentDateTime());
        appointment.setExpertId(request.getExpertId());
        appointment.setMemberId(memberId);
        appointment.setStatus("PENDING");
        appointment.setNotes(request.getNotes());
        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment updateAppointment(Long appointmentId, AppointmentRequest request, Long memberId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        
        if (!appointment.getMemberId().equals(memberId)) {
            throw new RuntimeException("Not authorized to update this appointment");
        }

        appointment.setAppointmentDateTime(request.getAppointmentDateTime());
        appointment.setExpertId(request.getExpertId());
        appointment.setNotes(request.getNotes());
        return appointmentRepository.save(appointment);
    }

    @Transactional
    public void deleteAppointment(Long appointmentId, Long memberId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        
        if (!appointment.getMemberId().equals(memberId)) {
            throw new RuntimeException("Not authorized to delete this appointment");
        }

        appointmentRepository.delete(appointment);
    }

    @Transactional
    public Appointment updateAppointmentStatus(Long appointmentId, String status, Long expertId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        
        if (!appointment.getExpertId().equals(expertId)) {
            throw new RuntimeException("Not authorized to update this appointment status");
        }

        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getMemberAppointments(Long memberId) {
        return appointmentRepository.findByMemberId(memberId);
    }

    public List<Appointment> getExpertAppointments(Long expertId) {
        return appointmentRepository.findByExpertId(expertId);
    }

    public Appointment getAppointment(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }
} 