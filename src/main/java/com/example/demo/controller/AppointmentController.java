package com.example.demo.controller;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Expert;
import com.example.demo.entity.User;
import com.example.demo.enums.EStatus;
import com.example.demo.payload.request.AppointmentRequest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.AuthenticationRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> createAppointment(
            @Valid @RequestBody AppointmentRequest request) {
        try {
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

            Appointment savedAppointment = appointmentRepository.save(appointment);
            return ResponseEntity.ok(new ApiResponse(true, "Appointment created successfully", savedAppointment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateAppointment(
            @PathVariable("id") Long appointmentId,
            @Valid @RequestBody AppointmentRequest request) {
        try {
            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));
            
            if (!Objects.equals(appointment.getMember().getId(), request.getMemberId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "Not authorized to update this appointment", null));
            }

            Expert expert = (Expert) authenticationRepository.findById(request.getExpertId())
                    .orElseThrow(() -> new RuntimeException("Expert not found"));

            appointment.setAppointmentDateTime(request.getAppointmentDateTime());
            appointment.setExpert(expert);
            appointment.setNotes(request.getNotes());
            
            Appointment updatedAppointment = appointmentRepository.save(appointment);
            return ResponseEntity.ok(new ApiResponse(true, "Appointment updated successfully", updatedAppointment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteAppointment(
            @PathVariable("id") Long appointmentId,
            @RequestBody AppointmentRequest request) {
        try {
            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));
            
            if (!Objects.equals(appointment.getMember().getId(), request.getMemberId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "Not authorized to delete this appointment", null));
            }

            appointmentRepository.delete(appointment);
            return ResponseEntity.ok(new ApiResponse(true, "Appointment deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_EXPERT')")
    public ResponseEntity<?> updateAppointmentStatus(
            @PathVariable("id") Long appointmentId,
            @RequestBody AppointmentRequest request) {
        try {
            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));
            
            if (!Objects.equals(appointment.getExpert().getId(), request.getExpertId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "Not authorized to update this appointment status", null));
            }

            appointment.setStatus(request.isStatus());
            Appointment updatedAppointment = appointmentRepository.save(appointment);
            return ResponseEntity.ok(new ApiResponse(true, "Appointment status updated successfully", updatedAppointment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getMemberAppointments(
            @PathVariable("memberId") Long memberId) {
        try {
            User member = authenticationRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            List<Appointment> appointments = appointmentRepository.findByMember(member);
            return ResponseEntity.ok(new ApiResponse(true, "Member appointments retrieved successfully", appointments));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/expert/{expertId}")
    @PreAuthorize("hasRole('ROLE_EXPERT')")
    public ResponseEntity<?> getExpertAppointments(
            @PathVariable("expertId") Long expertId) {
        try {
            Expert expert = (Expert) authenticationRepository.findById(expertId)
                    .orElseThrow(() -> new RuntimeException("Expert not found"));
            List<Appointment> appointments = appointmentRepository.findByExpert(expert);
            return ResponseEntity.ok(new ApiResponse(true, "Expert appointments retrieved successfully", appointments));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_EXPERT')")
    public ResponseEntity<?> getAppointment(@PathVariable("id") Long appointmentId) {
        try {
            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));
            return ResponseEntity.ok(new ApiResponse(true, "Appointment retrieved successfully", appointment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
} 