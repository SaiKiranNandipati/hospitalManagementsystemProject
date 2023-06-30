package com.hms.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.hms.app.dao.AppointmentRepo;
import com.hms.app.dao.DoctorRepo;
import com.hms.app.dao.PatientRepo;
import com.hms.app.dao.PaymentRepo;
import com.hms.app.model.Appointment;
import com.hms.app.model.Doctor;
import com.hms.app.model.Patient;
import com.hms.app.model.Payment;
import com.hms.app.service.AdminServiceImpl;
import com.hms.app.service.DoctorServiceImpl;
import com.hms.app.service.PatientServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Mock
    private DoctorRepo doctorRepo;
    
    @Mock
    private PatientRepo patientRepo;

    
    @Mock
    private AppointmentRepo appointmentRepo;



    
    @Test
    public void testgetAllDoctorAppointments() {

        Appointment app = new Appointment();
        
        app.setDate("23-7-2023");
        app.setDoctorEmail("doctor@gmail.com");
        app.setIsConfirmed("No");
        app.setIsPaid("Yes");
        app.setPatientEmail("aptient@gmail.com");
        
        List<Appointment> apps = new ArrayList<>();
        apps.add(app);
        
        when(appointmentRepo.findAll()).thenReturn(apps);

        assertEquals(apps, doctorService.getAllDoctorAppointments("doctor@gmail.com"));

    }
    
    
    
    
    
   
    
    

}