package com.example.demo.controller;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Expert;
import com.example.demo.entity.Feedback;
import com.example.demo.entity.User;
import com.example.demo.payload.request.FeedbackRequest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.AuthenticationRepository;
import com.example.demo.repository.FeedbackRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/feedback")
@SecurityRequirement(name = "api")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    // Create feedback for an appointment
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> createFeedback(@Valid @RequestBody FeedbackRequest request) {
        try {
            // Verify the appointment exists
            Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));

            // Verify the member is the one who had the appointment
            if (!Objects.equals(appointment.getMember().getId(), request.getMemberId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "Not authorized to give feedback for this appointment", null));
            }

            // Check if appointment has already been reviewed
            if (feedbackRepository.findByAppointmentId(appointment.getId()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Feedback has already been submitted for this appointment", null));
            }

            // Check if the appointment was completed (status = true)
            if (!appointment.isStatus()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Cannot provide feedback for appointments that haven't been completed", null));
            }

            // Create and save the feedback
            Feedback feedback = new Feedback();
            feedback.setAppointment(appointment);
            feedback.setRating(request.getRating());
            feedback.setComment(request.getComment());

            Feedback savedFeedback = feedbackRepository.save(feedback);
            
            // Update expert's rating
            Expert expert = appointment.getExpert();
            expert.updateRating(request.getRating());
            authenticationRepository.save(expert);

            return ResponseEntity.ok(new ApiResponse(true, "Feedback submitted successfully", savedFeedback));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    // Get feedback for a specific appointment
    @GetMapping("/appointment/{appointmentId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_EXPERT')")
    public ResponseEntity<?> getFeedbackByAppointment(@PathVariable Long appointmentId) {
        try {
            Feedback feedback = feedbackRepository.findByAppointmentId(appointmentId)
                    .orElse(null);
            
            if (feedback == null) {
                return ResponseEntity.ok(new ApiResponse(true, "No feedback found for this appointment", null));
            }
            
            return ResponseEntity.ok(new ApiResponse(true, "Feedback retrieved successfully", feedback));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    // Get all feedback for an expert
    @GetMapping("/expert/{expertId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_EXPERT')")
    public ResponseEntity<?> getFeedbackByExpert(@PathVariable Long expertId) {
        try {
            List<Feedback> feedback = feedbackRepository.findAllByExpertId(expertId);
            Double averageRating = feedbackRepository.getAverageRatingForExpert(expertId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("feedback", feedback);
            response.put("averageRating", averageRating != null ? averageRating : 0);
            response.put("totalReviews", feedback.size());
            
            return ResponseEntity.ok(new ApiResponse(true, "Expert feedback retrieved successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    // Get all feedback given by a member
    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getFeedbackByMember(@PathVariable Long memberId) {
        try {
            List<Feedback> feedback = feedbackRepository.findAllByMemberId(memberId);
            return ResponseEntity.ok(new ApiResponse(true, "Member feedback retrieved successfully", feedback));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    // Delete feedback (only allowed by the member who created it)
    @DeleteMapping("/{feedbackId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteFeedback(
            @PathVariable Long feedbackId,
            @RequestParam Long memberId) {
        try {
            Feedback feedback = feedbackRepository.findById(feedbackId)
                    .orElseThrow(() -> new RuntimeException("Feedback not found"));
                    
            if (!Objects.equals(feedback.getAppointment().getMember().getId(), memberId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "Not authorized to delete this feedback", null));
            }
            
            feedbackRepository.delete(feedback);
            return ResponseEntity.ok(new ApiResponse(true, "Feedback deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
} 