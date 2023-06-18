package com.hms.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.app.dao.DoctorRepo;
import com.hms.app.dao.PatientRepo;
import com.hms.app.model.Doctor;
import com.hms.app.model.Patient;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private DoctorRepo doctorRepo;

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
		
		return doctors.stream().filter(d -> d.getFirstname().contains(searchKey) || d.getLastname().contains(searchKey) || d.getUsername().contains(searchKey) || d.getEmail().contains(searchKey)).collect(Collectors.toList());
	}

	@Override
	public void deleteDoctor(Doctor doctor) {
		// TODO Auto-generated method stub
		doctorRepo.delete(doctor);
		
	}
	
	

}
