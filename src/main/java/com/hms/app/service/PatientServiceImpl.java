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
public class PatientServiceImpl implements PatientService{
	
	@Autowired
	private PatientRepo patientRepo;
	
	@Autowired
	private AppointmentRepo appointmentRepo;
	
	@Autowired
	private DoctorRepo doctorRepo;
	
	@Autowired
	private PaymentRepo paymentRepo;

	@Override
	public Patient findPatient(String email) {
		// TODO Auto-generated method stub
		List<Patient> patients = patientRepo.findAll();
		System.out.println("----"+patients.size());
		if(patients.size() == 0) {
			return null;
		}
		List<Patient> veifiedPatient = patients.stream().filter(n -> n.getEmail().equals(email)).collect(Collectors.toList());
		if(veifiedPatient.size() > 0) {
			return veifiedPatient.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public Patient findPatientByUsername(String username) {
		List<Patient> patients = patientRepo.findAll();
		List<Patient> veifiedPatient = patients.stream().filter(n -> n.getUsername().equals(username)).collect(Collectors.toList());
		if(veifiedPatient.size() > 0) {
			return veifiedPatient.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public int savePatient(Patient patient) {
		patientRepo.save(patient);
		if(patientRepo.save(patient)!=null) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public String authenticatePatient(Patient patient) {
		
		if(patient.getEmail().equals("admin@gmail.com") && patient.getPassword().equals("admin")) {
			
			
			return "admin";
		}
		
		
		List<Patient> patients = patientRepo.findAll();
		List<Patient> veifiedPatient = patients.stream().filter(n -> (n.getEmail().equals(patient.getEmail()) || n.getUsername().equals(patient.getEmail())) && n.getPassword().equals(patient.getPassword())).collect(Collectors.toList());
		
		List<Doctor> doctors = doctorRepo.findAll();
		List<Doctor> veifiedDoctor = doctors.stream().filter(n -> (n.getEmail().equals(patient.getEmail()) || n.getUsername().equals(patient.getEmail())) && n.getPassword().equals(patient.getPassword())).collect(Collectors.toList());
		
		
		if(veifiedPatient.size() ==1) {
			return "patient";
		}
		else if(veifiedDoctor.size() == 1) {
			return "doctor";
		}
		else {
			return null;
		}
			
	}

	@Override
	public Patient getPatientByEmail(String email) {
		// TODO Auto-generated method stub
		return patientRepo.findPatientByEmail(email);
	}

	@Override
	public Patient getPatientById(Long id) {
		// TODO Auto-generated method stub
		return patientRepo.findPatientById(id);
	}

	@Override
	public void deletePatient(Patient patient) {
		// TODO Auto-generated method stub
		patientRepo.delete(patient);
		
	}

	@Override
	public Appointment saveAppointment(Appointment appointment) {
		// TODO Auto-generated method stub
		Appointment app = appointmentRepo.save(appointment);
		
		
		return app;
		
	}

	@Override
	public List<Appointment> getAllAppointments(String email) {
		// TODO Auto-generated method stub
		return appointmentRepo.findAll().stream().filter(app -> app.getPatientEmail().equals(email)).collect(Collectors.toList());
	}

	@Override
	public List<Doctor> getAllDoctors() {
		// TODO Auto-generated method stub
		return doctorRepo.findAll();
	}

	@Override
	public void cancelAppointment(Long id) {
		// TODO Auto-generated method stub
		
		Appointment app = appointmentRepo.findAll().stream().filter(ap -> ap.getId().equals(id)).collect(Collectors.toList()).get(0);
		
		Payment bill = paymentRepo.findAll().stream().filter(bil -> bil.getAppointmentId().equals(String.valueOf(app.getId()))).collect(Collectors.toList()).get(0);
		
		app.setIsConfirmed("Cancelled");
		bill.setIsValid("Money Refunded");
		
		appointmentRepo.save(app);
		paymentRepo.save(bill);
	}

	@Override
	public void savePayment(Payment payment) {
		// TODO Auto-generated method stub
		paymentRepo.save(payment);
	}

	@Override
	public List<Payment> getAllPayments(String email) {
		// TODO Auto-generated method stub
		return paymentRepo.findAll().stream().filter(p -> p.getPatientEmail().equals(email)).collect(Collectors.toList());
	}

	@Override
	public int validatePassword(Patient patient, String securityQuestion, String securityAnswer) {
		List<Patient> patients = patientRepo.findAll();
		List<Patient> verifiedPatient = patients.stream().filter(n -> n.getEmail().equals(patient.getEmail())).collect(Collectors.toList());
		if(verifiedPatient.size() ==1) {
			List<Patient> userSecurities = patientRepo.findAll();
			
			List<Patient> securedUser = userSecurities.stream().filter(security -> security.getSecurityQuestion().equals(securityQuestion) && security.getSecurityAnswer().equals(securityAnswer)
					
					).collect(Collectors.toList());
			if(securedUser.size() == 1) {
				return 1;
			}
			else {
				return 2;
			}
		}
		else {
			List<Doctor> doctors = doctorRepo.findAll();
			List<Doctor> verifiedDoctor = doctors.stream().filter(n -> n.getEmail().equals(patient.getEmail())).collect(Collectors.toList());
			if(verifiedDoctor.size() ==1) {
				List<Doctor> doctorSecurities = doctorRepo.findAll();
				
				List<Doctor> securedDoctor = doctorSecurities.stream().filter(security -> security.getSecurityQuestion().equals(securityQuestion) && security.getSecurityAnswer().equals(securityAnswer)
						
						).collect(Collectors.toList());
				if(securedDoctor.size() == 1) {
					return 1;
				}
				else {
					return 2;
				}
			}
			else {
				return 0;
			}
		}
	}

	@Override
	public void saveNewPassword(Patient patient) {
		Patient validPatient = patientRepo.findPatientByEmail(patient.getEmail());
		if(validPatient == null) {
			Doctor validDoctor = doctorRepo.findDoctorByEmail(patient.getEmail());
			validDoctor.setPassword(patient.getPassword());
			doctorRepo.save(validDoctor);
		}
		validPatient.setPassword(patient.getPassword());
		patientRepo.save(validPatient);
		
	}
	
	

}
