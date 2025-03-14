package com.example.service;

import com.example.model.Appointment;
import com.example.repository.AppointmentRepository;
import com.example.payload.request.AppointmentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private AppointmentRequest appointmentRequest;
    private Appointment appointment;
    private final Long MEMBER_ID = 1L;
    private final Long EXPERT_ID = 2L;
    private final Long APPOINTMENT_ID = 1L;

    @BeforeEach
    void setUp() {
        LocalDateTime appointmentTime = LocalDateTime.now().plusDays(1);
        
        appointmentRequest = new AppointmentRequest();
        appointmentRequest.setAppointmentDateTime(appointmentTime);
        appointmentRequest.setExpertId(EXPERT_ID);
        appointmentRequest.setNotes("Test appointment");

        appointment = new Appointment();
        appointment.setId(APPOINTMENT_ID);
        appointment.setAppointmentDateTime(appointmentTime);
        appointment.setExpertId(EXPERT_ID);
        appointment.setMemberId(MEMBER_ID);
        appointment.setStatus("PENDING");
        appointment.setNotes("Test appointment");
    }

    @Test
    void createAppointment_Success() {
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment created = appointmentService.createAppointment(appointmentRequest, MEMBER_ID);

        assertNotNull(created);
        assertEquals(MEMBER_ID, created.getMemberId());
        assertEquals(EXPERT_ID, created.getExpertId());
        assertEquals("PENDING", created.getStatus());
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    void updateAppointment_Success() {
        when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment updated = appointmentService.updateAppointment(APPOINTMENT_ID, appointmentRequest, MEMBER_ID);

        assertNotNull(updated);
        assertEquals(appointmentRequest.getAppointmentDateTime(), updated.getAppointmentDateTime());
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    void updateAppointment_NotAuthorized() {
        when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));

        assertThrows(RuntimeException.class, () -> 
            appointmentService.updateAppointment(APPOINTMENT_ID, appointmentRequest, 999L)
        );
    }

    @Test
    void deleteAppointment_Success() {
        when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));
        doNothing().when(appointmentRepository).delete(appointment);

        assertDoesNotThrow(() -> 
            appointmentService.deleteAppointment(APPOINTMENT_ID, MEMBER_ID)
        );
        verify(appointmentRepository).delete(appointment);
    }

    @Test
    void updateAppointmentStatus_Success() {
        when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment updated = appointmentService.updateAppointmentStatus(APPOINTMENT_ID, "ACCEPTED", EXPERT_ID);

        assertNotNull(updated);
        assertEquals("ACCEPTED", updated.getStatus());
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    void getMemberAppointments_Success() {
        List<Appointment> appointments = Arrays.asList(appointment);
        when(appointmentRepository.findByMemberId(MEMBER_ID)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getMemberAppointments(MEMBER_ID);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(appointmentRepository).findByMemberId(MEMBER_ID);
    }

    @Test
    void getExpertAppointments_Success() {
        List<Appointment> appointments = Arrays.asList(appointment);
        when(appointmentRepository.findByExpertId(EXPERT_ID)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getExpertAppointments(EXPERT_ID);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(appointmentRepository).findByExpertId(EXPERT_ID);
    }

    @Test
    void getAppointment_Success() {
        when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));

        Appointment result = appointmentService.getAppointment(APPOINTMENT_ID);

        assertNotNull(result);
        assertEquals(APPOINTMENT_ID, result.getId());
        verify(appointmentRepository).findById(APPOINTMENT_ID);
    }

    @Test
    void getAppointment_NotFound() {
        when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> 
            appointmentService.getAppointment(APPOINTMENT_ID)
        );
    }
} 