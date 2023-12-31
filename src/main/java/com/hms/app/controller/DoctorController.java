package com.hms.app.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hms.app.model.Appointment;
import com.hms.app.model.Doctor;
import com.hms.app.model.Patient;
import com.hms.app.model.Payment;
import com.hms.app.service.AdminService;
import com.hms.app.service.DoctorService;
import com.hms.app.service.PatientService;



@Controller
public class DoctorController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private DoctorService doctorService;
	
	
	

	@GetMapping("/doctor")
	public String getPatientWelcomePage(Model model, HttpSession session)
	{
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String email = messages.get(0);
		List<Appointment> appointments = doctorService.getAllDoctorAppointments(email);
		model.addAttribute("appointments", appointments);
		model.addAttribute("size", appointments.size());
		
		return "doctor/welcomedoctor";
	}
	
	
	
	
	
	
	
	
	@GetMapping("/cancelAppointments/{id}")
	public String cancelAppointment(Model model, HttpSession session, @PathVariable(name="id") Long id) {

		patientService.cancelAppointment(id);
		return "redirect:/doctor";
	}
	
	@GetMapping("/confirmAppointment/{id}")
	public String confirmAppointment(Model model, HttpSession session, @PathVariable(name="id") Long id) {

		doctorService.confirmAppointment(id);
		return "redirect:/doctor";
	}

	@GetMapping("/addPrescription/{id}")
	public String editDoctor(Model model, HttpSession session, @PathVariable(name="id") Long id) {
		
		
	
		Appointment appointment = doctorService.getAppointmentById(id);
		
		model.addAttribute("appointment", appointment);
		

		return "doctor/addprescription";
	}
	
	@PostMapping("/savePrescription")
	public String saveAppointment(@ModelAttribute("appointment") Appointment appointment,HttpServletRequest request,Model model,HttpSession session) {
		
		Appointment app = patientService.saveAppointment(appointment);
		
		
		
		
		return "redirect:/doctor";
		
	}
	
	@GetMapping("/viewPatient/{email}")
	public String getPatientProfile(Model model, HttpSession session,  @PathVariable(name="email") String email)
	{
		
        
		Patient patientt = patientService.getPatientByEmail(email);
		model.addAttribute("patient", patientt);
		return "doctor/viewpatient";
	}
}
