package com.hms.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hms.app.model.Doctor;
import com.hms.app.model.Patient;


@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long> {
	
	@Query( value = "select * from doctors where id = :id", nativeQuery = true)
	Doctor findDoctorById(@Param("id") Long id);

}
