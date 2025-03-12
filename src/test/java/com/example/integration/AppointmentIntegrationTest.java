package com.example.integration;

import com.example.demo.DemoApplication;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Expert;
import com.example.demo.entity.User;
import com.example.demo.payload.request.AppointmentRequest;
import com.example.demo.enums.EStatus;
import com.example.demo.model.ERole;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.AuthenticationRepository;
import com.example.demo.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
    classes = DemoApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public class AppointmentIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private TokenService tokenService;

    private AppointmentRequest appointmentRequest;
    private HttpHeaders headers;
    private User member;
    private Expert expert;
    private String memberToken;
    private String expertToken;

    @BeforeEach
    void setUp() {
        appointmentRepository.deleteAll();
        authenticationRepository.deleteAll();

        // Create test member
        member = new User();
        member.setUsername("testmember");
        member.setEmail("member@test.com");
        member.setPassword("password");
        member.setRole(ERole.ROLE_USER);
        member = authenticationRepository.save(member);

        // Create test expert
        expert = new Expert();
        expert.setUsername("testexpert");
        expert.setEmail("expert@test.com");
        expert.setPassword("password");
        expert.setRole(ERole.ROLE_EXPERT);
        expert = (Expert) authenticationRepository.save(expert);

        LocalDateTime appointmentTime = LocalDateTime.now().plusDays(1);
        
        appointmentRequest = new AppointmentRequest();
        appointmentRequest.setAppointmentDateTime(appointmentTime);
        appointmentRequest.setExpertId(expert.getId());
        appointmentRequest.setNotes("Test appointment");

        // Set up headers with authentication
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-User-Id", String.valueOf(member.getId()));
        
        // Generate tokens
        memberToken = tokenService.generateToken(member);
        expertToken = tokenService.generateToken(expert);
        headers.setBearerAuth(memberToken);
    }

    @Test
    void fullAppointmentFlow() {
        // Create appointment
        HttpEntity<AppointmentRequest> createRequest = new HttpEntity<>(appointmentRequest, headers);
        ResponseEntity<Appointment> createResponse = restTemplate
                .postForEntity("/api/appointments", createRequest, Appointment.class);
        
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        Long appointmentId = createResponse.getBody().getId();

        // Update appointment
        appointmentRequest.setNotes("Updated notes");
        HttpEntity<AppointmentRequest> updateRequest = new HttpEntity<>(appointmentRequest, headers);
        ResponseEntity<Appointment> updateResponse = restTemplate.exchange(
                "/api/appointments/" + appointmentId,
                HttpMethod.PUT,
                updateRequest,
                Appointment.class
        );

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertEquals("Updated notes", updateResponse.getBody().getNotes());

        // Expert approves appointment
        headers.set("X-User-Id", String.valueOf(expert.getId()));
        headers.setBearerAuth(expertToken);
        
        HttpEntity<String> acceptRequest = new HttpEntity<>(headers);
        ResponseEntity<Appointment> acceptResponse = restTemplate.exchange(
                "/api/appointments/" + appointmentId + "/status?status=APPROVED",
                HttpMethod.PUT,
                acceptRequest,
                Appointment.class
        );

        assertEquals(HttpStatus.OK, acceptResponse.getStatusCode());
        assertEquals(EStatus.APPROVED, acceptResponse.getBody().getStatus());

        // View appointment
        ResponseEntity<Appointment> viewResponse = restTemplate.exchange(
                "/api/appointments/" + appointmentId,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Appointment.class
        );

        assertEquals(HttpStatus.OK, viewResponse.getStatusCode());
        assertNotNull(viewResponse.getBody());
        assertEquals(EStatus.APPROVED, viewResponse.getBody().getStatus());

        // Delete appointment
        headers.set("X-User-Id", String.valueOf(member.getId()));
        headers.setBearerAuth(memberToken);
        HttpEntity<String> deleteRequest = new HttpEntity<>(headers);
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/appointments/" + appointmentId,
                HttpMethod.DELETE,
                deleteRequest,
                Void.class
        );

        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertFalse(appointmentRepository.existsById(appointmentId));
    }
} 