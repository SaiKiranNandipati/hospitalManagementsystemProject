package com.hms.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.app.dao.AppointmentRepo;
import com.hms.app.dao.DoctorRepo;
import com.hms.app.dao.PatientRepo;
import com.hms.app.dao.PaymentRepo;
import com.hms.app.model.Appointment;


@Service
public class DoctorServiceImpl implements DoctorService{
	
	@Autowired
	private PatientRepo patientRepo;
	
	@Autowired
	private AppointmentRepo appointmentRepo;
	
	@Autowired
	private DoctorRepo doctorRepo;
	
	@Autowired
	private PaymentRepo paymentRepo;

	@Override
	public List<Appointment> getAllDoctorAppointments(String email) {
		// TODO Auto-generated method stub
		return appointmentRepo.findAll().stream().filter(app -> app.getDoctorEmail().equals(email)).collect(Collectors.toList());
	}

	@Override
	public void confirmAppointment(Long id) {
		// TODO Auto-generated method stub
		
		Appointment appointment = appointmentRepo.findAll().stream().filter(app -> app.getId().equals(id)).collect(Collectors.toList()).get(0);
		appointment.setIsConfirmed("Confirmed");
		appointmentRepo.save(appointment);
	
	}

	
}
