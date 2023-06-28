package com.hms.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hms.app.model.Doctor;
import com.hms.app.model.Patient;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Long> {

	@Query( value = "select * from patients where email = :email", nativeQuery = true)
	Patient findPatientByEmail(@Param("email") String email);
	
	@Query( value = "select * from patients where id = :id", nativeQuery = true)
	Patient findPatientById(@Param("id") Long id);
}
