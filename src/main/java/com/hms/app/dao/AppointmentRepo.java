package com.hms.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hms.app.model.Appointment;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

	@Query( value = "select * from appointment where id = :id", nativeQuery = true)
	Appointment findAppointmentById(@Param("id") Long id);

}
