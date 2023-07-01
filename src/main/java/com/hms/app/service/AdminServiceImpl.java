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
import com.hms.app.model.Doctor;
import com.hms.app.model.Patient;
import com.hms.app.model.Payment;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private DoctorRepo doctorRepo;
	
	@Autowired
	private PaymentRepo billRepo;
	
	@Autowired
	private AppointmentRepo appointmentRepo;
	
	@Autowired
	private PatientRepo patientRepo;

	@Override
	public void saveDoctor(Doctor doctor) {
		// TODO Auto-generated method stub
		doctorRepo.save(doctor);
	}

	@Override
	public List<Doctor> getAllDoctors() {
		// TODO Auto-generated method stub
		return doctorRepo.findAll();
	}

	@Override
	public Doctor getDoctorById(Long id) {
		// TODO Auto-generated method stub
		return doctorRepo.findDoctorById(id);
	}

	@Override
	public void updateDoctor(Doctor doctor) {
		// TODO Auto-generated method stub
		
		doctorRepo.save(doctor);
	}

	@Override
	public List<Doctor> searchDoctor(String searchKey) {
		// TODO Auto-generated method stub
		List<Doctor> doctors = doctorRepo.findAll();
		
		return doctors.stream().filter(d -> d.getFirstname().contains(searchKey) || d.getLastname().contains(searchKey) || d.getUsername().contains(searchKey) || d.getEmail().contains(searchKey) || d.getSpecialist().contains(searchKey) || d.getSpecialist().equalsIgnoreCase(searchKey)).collect(Collectors.toList());
	}

	@Override
	public void deleteDoctor(Doctor doctor) {
		// TODO Auto-generated method stub
		doctorRepo.delete(doctor);
		
	}

	@Override
	public List<Payment> getAllBills() {
		// TODO Auto-generated method stub
		return billRepo.findAll();
	}

	@Override
	public void approveBill(Long id) {
		Payment bil = billRepo.findAll().stream().filter(bill -> bill.getId().equals(id)).collect(Collectors.toList()).get(0);
		
		bil.setIsValid("Approved");
		billRepo.save(bil);
	}

	@Override
	public List<Appointment> getAllAppointments() {
		// TODO Auto-generated method stub
		return appointmentRepo.findAll();
	}

	@Override
	public List<Patient> getAllPatients() {
		// TODO Auto-generated method stub
		return patientRepo.findAll();
	}

	@Override
	public void rejectBill(Long id) {
		// TODO Auto-generated method stub
		
		Payment bil = billRepo.findAll().stream().filter(bill -> bill.getId().equals(id)).collect(Collectors.toList()).get(0);
		Appointment app = appointmentRepo.findAll().stream().filter(ap -> String.valueOf(ap.getId()).equals(bil.getAppointmentId())).collect(Collectors.toList()).get(0);
		
		app.setIsConfirmed("Cancelled");
		bil.setIsValid("Money Refunded");
		billRepo.save(bil);
		appointmentRepo.save(app);
		
	}
	
	

}
