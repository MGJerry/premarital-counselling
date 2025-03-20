package com.example.service;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Expert;
import com.example.demo.entity.Feedback;
import com.example.demo.entity.User;
import com.example.demo.enums.EGender;
import com.example.demo.model.ERole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackServiceTest {

    private User member;
    private Expert expert;
    private Appointment appointment;
    private Feedback feedback;

    @BeforeEach
    void setUp() {
        // Create test user
        member = new User();
        member.setId(1L);
        member.setUsername("testuser");
        member.setEmail("test@example.com");
        member.setRole(ERole.ROLE_USER);

        // Create test expert
        expert = new Expert();
        expert.setId(2L);
        expert.setUsername("testexpert");
        expert.setEmail("expert@example.com");
        expert.setRole(ERole.ROLE_EXPERT);
        expert.setAverageRating(0.0);
        expert.setTotalRatings(0);

        // Create test appointment
        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setMember(member);
        appointment.setExpert(expert);
        appointment.setAppointmentDateTime(LocalDateTime.now().plusDays(1));
        appointment.setStatus(true); // Completed appointment
        appointment.setNotes("Test appointment");

        // Create test feedback
        feedback = new Feedback();
        feedback.setId(1L);
        feedback.setAppointment(appointment);
        feedback.setRating(5);
        feedback.setComment("Great service!");
        feedback.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testAddFeedback() {
        // Simulate creating feedback and updating expert rating
        expert.updateRating(feedback.getRating());
        
        assertEquals(5.0, expert.getAverageRating());
        assertEquals(1, expert.getTotalRatings());
        assertEquals("Great service!", feedback.getComment());
        assertEquals(5, feedback.getRating());
    }
    
    @Test
    void testUpdateExpertRating() {
        // Initial expert has no ratings
        assertEquals(0.0, expert.getAverageRating());
        assertEquals(0, expert.getTotalRatings());
        
        // First rating: 5
        expert.updateRating(5);
        assertEquals(5.0, expert.getAverageRating());
        assertEquals(1, expert.getTotalRatings());
        
        // Second rating: 3
        expert.updateRating(3);
        assertEquals(4.0, expert.getAverageRating()); // (5+3)/2 = 4
        assertEquals(2, expert.getTotalRatings());
        
        // Third rating: 4
        expert.updateRating(4);
        assertEquals(4.0, expert.getAverageRating()); // (5+3+4)/3 = 4
        assertEquals(3, expert.getTotalRatings());
    }
    
    @Test
    void testCalculateAverageRating() {
        // Create multiple feedbacks with different ratings
        expert.updateRating(5); // First feedback
        expert.updateRating(3); // Second feedback 
        expert.updateRating(4); // Third feedback
        
        // Check the final average
        assertEquals(4.0, expert.getAverageRating());
        assertEquals(3, expert.getTotalRatings());
    }
} 