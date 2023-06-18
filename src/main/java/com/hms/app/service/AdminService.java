package com.hms.app.service;

import java.util.List;

import com.hms.app.model.Doctor;
import com.hms.app.model.Patient;

public interface AdminService {

	void saveDoctor(Doctor doctor);

	List<Doctor> getAllDoctors();

	Doctor getDoctorById(Long id);

	void updateDoctor(Doctor doctor);

	List<Doctor> searchDoctor(String searchKey);

	void deleteDoctor(Doctor doctor);


}
