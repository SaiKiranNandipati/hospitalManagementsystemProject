package com.hms.app.service;

import java.util.List;

import com.hms.app.model.Appointment;
import com.hms.app.model.Doctor;
import com.hms.app.model.Patient;
import com.hms.app.model.Payment;

public interface PatientService {

	Patient findPatient(String email);

	Patient findPatientByUsername(String username);

	int savePatient(Patient patient);

	String authenticatePatient(Patient patient);

	Patient getPatientByEmail(String email);

	Patient getPatientById(Long id);

	void deletePatient(Patient patient);

	void saveAppointment(Appointment appointment);

	List<Appointment> getAllAppointments(String email);

	List<Doctor> getAllDoctors();

	void cancelAppointment(Long id);

	void savePayment(Payment payment);

	List<Payment> getAllPayments(String email);

}
