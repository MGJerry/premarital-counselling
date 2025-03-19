package com.example.demo.controller;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Expert;
import com.example.demo.entity.User;
import com.example.demo.enums.EStatus;
import com.example.demo.payload.request.AppointmentRequest;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.AuthenticationRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/appointments")
@SecurityRequirement(name = "api")
public class AppointmentController {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Appointment> createAppointment(
            @Valid @RequestBody AppointmentRequest request) {
        User member = authenticationRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Expert expert = (Expert) authenticationRepository.findById(request.getExpertId())
                .orElseThrow(() -> new RuntimeException("Expert not found"));

        Appointment appointment = new Appointment();
        appointment.setAppointmentDateTime(request.getAppointmentDateTime());
        appointment.setMember(member);
        appointment.setExpert(expert);
        appointment.setNotes(request.getNotes());
        appointment.setStatus(false);

        return ResponseEntity.ok(appointmentRepository.save(appointment));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable("id") Long appointmentId,
            @Valid @RequestBody AppointmentRequest request) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        
        if (!Objects.equals(appointment.getMember().getId(), request.getMemberId())) {
            throw new RuntimeException("Not authorized to update this appointment");
        }

        Expert expert = (Expert) authenticationRepository.findById(request.getExpertId())
                .orElseThrow(() -> new RuntimeException("Expert not found"));

        appointment.setAppointmentDateTime(request.getAppointmentDateTime());
        appointment.setExpert(expert);
        appointment.setNotes(request.getNotes());
        return ResponseEntity.ok(appointmentRepository.save(appointment));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteAppointment(
            @PathVariable("id") Long appointmentId,
            @RequestBody AppointmentRequest request) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        
        if (!Objects.equals(appointment.getMember().getId(), request.getMemberId())) {
            throw new RuntimeException("Not authorized to delete this appointment");
        }

        appointmentRepository.delete(appointment);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_EXPERT')")
    public ResponseEntity<Appointment> updateAppointmentStatus(
            @PathVariable("id") Long appointmentId,
            @RequestBody AppointmentRequest request) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        
        if (!Objects.equals(appointment.getExpert().getId(), request.getExpertId())) {
            throw new RuntimeException("Not authorized to update this appointment status");
        }

        appointment.setStatus(request.isStatus());
        return ResponseEntity.ok(appointmentRepository.save(appointment));
    }

    @GetMapping("/member")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Appointment>> getMemberAppointments(
            @RequestBody AppointmentRequest request) {
        User member = authenticationRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        return ResponseEntity.ok(appointmentRepository.findByMember(member));
    }

    @GetMapping("/expert")
    @PreAuthorize("hasRole('ROLE_EXPERT')")
    public ResponseEntity<List<Appointment>> getExpertAppointments(
            @RequestBody AppointmentRequest request) {
        Expert expert = (Expert) authenticationRepository.findById(request.getExpertId())
                .orElseThrow(() -> new RuntimeException("Expert not found"));
        return ResponseEntity.ok(appointmentRepository.findByExpert(expert));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_EXPERT')")
    public ResponseEntity<Appointment> getAppointment(@PathVariable("id") Long appointmentId) {
        return ResponseEntity.ok(appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found")));
    }
} 