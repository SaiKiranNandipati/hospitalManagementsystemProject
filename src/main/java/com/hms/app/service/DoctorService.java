package com.hms.app.service;

import java.util.List;

import com.hms.app.model.Appointment;
import com.hms.app.model.Doctor;
import com.hms.app.model.Patient;
import com.hms.app.model.Payment;

public interface DoctorService {

	List<Appointment> getAllDoctorAppointments(String email);

	void confirmAppointment(Long id);

	

}
