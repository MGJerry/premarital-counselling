package com.example.integration;

import com.example.demo.DemoApplication;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Expert;
import com.example.demo.entity.User;
import com.example.demo.payload.request.AppointmentRequest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.model.ERole;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.AuthenticationRepository;
import com.example.demo.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Map;

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
        appointmentRequest.setMemberId(member.getId());
        appointmentRequest.setNotes("Test appointment");
        appointmentRequest.setStatus(true);

        // Set up headers with authentication
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Generate tokens
        memberToken = tokenService.generateToken(member);
        expertToken = tokenService.generateToken(expert);
        headers.setBearerAuth(memberToken);
    }

    @Test
    void fullAppointmentFlow() {
        // Create appointment
        HttpEntity<AppointmentRequest> createRequest = new HttpEntity<>(appointmentRequest, headers);
        ResponseEntity<ApiResponse> createResponse = restTemplate
                .exchange("/api/appointments", 
                        HttpMethod.POST,
                        createRequest, 
                        new ParameterizedTypeReference<ApiResponse>() {});
        
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertTrue(createResponse.getBody().isSuccess());
        
        Map<String, Object> appointmentMap = (Map<String, Object>) createResponse.getBody().getData();
        Long appointmentId = ((Number) appointmentMap.get("id")).longValue();
        
        // Update appointment
        appointmentRequest.setNotes("Updated notes");
        HttpEntity<AppointmentRequest> updateRequest = new HttpEntity<>(appointmentRequest, headers);
        ResponseEntity<ApiResponse> updateResponse = restTemplate.exchange(
                "/api/appointments/" + appointmentId,
                HttpMethod.PUT,
                updateRequest,
                new ParameterizedTypeReference<ApiResponse>() {}
        );

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertTrue(updateResponse.getBody().isSuccess());
        appointmentMap = (Map<String, Object>) updateResponse.getBody().getData();
        assertEquals("Updated notes", appointmentMap.get("notes"));

        // Expert approves appointment
        headers.setBearerAuth(expertToken);
        
        HttpEntity<AppointmentRequest> acceptRequest = new HttpEntity<>(appointmentRequest, headers);
        ResponseEntity<ApiResponse> acceptResponse = restTemplate.exchange(
                "/api/appointments/" + appointmentId + "/status",
                HttpMethod.PUT,
                acceptRequest,
                new ParameterizedTypeReference<ApiResponse>() {}
        );

        assertEquals(HttpStatus.OK, acceptResponse.getStatusCode());
        assertTrue(acceptResponse.getBody().isSuccess());
        appointmentMap = (Map<String, Object>) acceptResponse.getBody().getData();
        assertTrue((Boolean) appointmentMap.get("status"));

        // View appointment
        ResponseEntity<ApiResponse> viewResponse = restTemplate.exchange(
                "/api/appointments/" + appointmentId,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<ApiResponse>() {}
        );

        assertEquals(HttpStatus.OK, viewResponse.getStatusCode());
        assertTrue(viewResponse.getBody().isSuccess());
        appointmentMap = (Map<String, Object>) viewResponse.getBody().getData();
        assertTrue((Boolean) appointmentMap.get("status"));

        // Delete appointment
        headers.setBearerAuth(memberToken);
        HttpEntity<AppointmentRequest> deleteRequest = new HttpEntity<>(appointmentRequest, headers);
        ResponseEntity<ApiResponse> deleteResponse = restTemplate.exchange(
                "/api/appointments/" + appointmentId,
                HttpMethod.DELETE,
                deleteRequest,
                new ParameterizedTypeReference<ApiResponse>() {}
        );

        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertTrue(deleteResponse.getBody().isSuccess());
        assertFalse(appointmentRepository.existsById(appointmentId));
    }
} 