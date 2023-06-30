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
import com.hms.app.service.PatientServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @InjectMocks
    private PatientServiceImpl patientService;

    @Mock
    private DoctorRepo doctorRepo;
    
    @Mock
    private PatientRepo patientRepo;

    @Mock
    private PaymentRepo paymentRepo;
    
    @Mock
    private AppointmentRepo appointmentRepo;



    
    @Test
    public void testgetAllDoctors() {

        Doctor doctor = new Doctor();
        
        doctor.setEmail("doctor@gmail.com");
        doctor.setUsername("doctor");
        doctor.setPassword("doctor");
        doctor.setFirstname("Doctor");
        doctor.setLastname("Doctor");
        doctor.setMobileNumber("1234567890");
        doctor.setDoorNo("123");
        doctor.setExperience("12");
        doctor.setSpecialist("Gynacologist");
        
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);
        
        when(doctorRepo.findAll()).thenReturn(doctors);

        assertEquals(doctors, patientService.getAllDoctors());

    }
    
    
    
    @Test
    public void testgetAllBills() {

        Payment bill = new Payment();
        
        bill.setAmount("500");
        bill.setAppointmentId("1");
        bill.setCardName("CardName");
        bill.setCardNumber("12345678");
        bill.setCvv("123");
        bill.setDate("12-12-1212");
        bill.setExpiryDate("20-20-2022");
        bill.setPatientEmail("patient@gmail.com");
        
        List<Payment> bills = new ArrayList<>();
        bills.add(bill);
        
        when(paymentRepo.findAll()).thenReturn(bills);

        assertEquals(bills, patientService.getAllPayments("patient@gmail.com"));

    }
    
    @Test
    public void testgetAllAppointments() {

        Appointment app = new Appointment();
        
        app.setDate("23-7-2023");
        app.setDoctorEmail("doctor@gmail.com");
        app.setIsConfirmed("No");
        app.setIsPaid("Yes");
        app.setPatientEmail("aptient@gmail.com");
        
        List<Appointment> apps = new ArrayList<>();
        apps.add(app);
        
        when(appointmentRepo.findAll()).thenReturn(apps);

        assertEquals(apps, patientService.getAllAppointments("aptient@gmail.com"));

    }
    
    
    
   
    
    

}