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

import com.hms.app.dao.DoctorRepo;
import com.hms.app.dao.PatientRepo;
import com.hms.app.dao.PaymentRepo;
import com.hms.app.model.Doctor;
import com.hms.app.model.Patient;
import com.hms.app.model.Payment;
import com.hms.app.service.AdminServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private DoctorRepo doctorRepo;
    
    @Mock
    private PatientRepo patientRepo;

    @Mock
    private PaymentRepo paymentRepo;



    
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

        assertEquals(doctors, adminService.getAllDoctors());

    }
    
    @Test
    public void testgetAllPatients() {

        Patient patient = new Patient();
        
        patient.setEmail("doctor@gmail.com");
        patient.setUsername("doctor");
        patient.setPassword("doctor");
        patient.setFirstname("Doctor");
        patient.setLastname("Doctor");
        patient.setMobileNumber("1234567890");
        patient.setDoorNo("123");
        patient.setBloodGroup("A+ve");
        
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        
        when(patientRepo.findAll()).thenReturn(patients);

        assertEquals(patients, adminService.getAllPatients());

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
        
        List<Payment> bills = new ArrayList<>();
        bills.add(bill);
        
        when(paymentRepo.findAll()).thenReturn(bills);

        assertEquals(bills, adminService.getAllBills());

    }
    
    
    
   
    
    

}