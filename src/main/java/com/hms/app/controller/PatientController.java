package com.hms.app.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
import com.hms.app.service.PatientService;



@Controller
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	
	

	@GetMapping("/patient")
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
        
		List<Appointment> appointments = patientService.getAllAppointments(email);
		model.addAttribute("appointments", appointments);
		model.addAttribute("size", appointments.size());
		return "patient/welcomepatient";
	}
	
	@GetMapping("/patientProfile")
	public String getPatientProfile(Model model, HttpSession session)
	{
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String email = messages.get(0);
        
		Patient patientt = patientService.getPatientByEmail(email);
		model.addAttribute("patient", patientt);
		return "patient/profile";
	}
	
	@GetMapping("/editprofile")
	public String editProfile(Model model, HttpSession session)
	{
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String email = messages.get(0);
        
		Patient patientt = patientService.getPatientByEmail(email);
		model.addAttribute("patient", patientt);
		return "patient/editprofile";
	}
	
	@GetMapping("/makeAppointment")
	public String makeAppointment(Model model) {

		Appointment appointment = new Appointment();
		model.addAttribute("appointment", appointment);
		List<Doctor> doctors = patientService.getAllDoctors();
		model.addAttribute("doctors", doctors);
		
		
		List<String> catList= new ArrayList<String>();
		catList.add("Cardiologist");
		catList.add("Gynacologist");
		catList.add("Neurologist");
		catList.add("Orthopedic");
		catList.add("Dental");
		catList.add("ENT");
		
		
		
		HashMap<String, List<String>> itemMap = new HashMap<>();
		
		
		for(int i=0;i<catList.size();i++)
		{
			
			String category = catList.get(i);
			
			List<String> items = doctors.stream()
									.filter(it -> it.getSpecialist().equals(category))
									.map(it-> it.getEmail()+","+it.getUsername())
									.collect(Collectors.toList());
			
			
			
			itemMap.put(category, items);
			
			
			
		}
		
		model.addAttribute("categories", catList);
		model.addAttribute("itemMap", itemMap);

		return "patient/makeappointment";
	}
	
	@PostMapping("/saveAppointment")
	public String saveAppointment(@ModelAttribute("appointment") Appointment appointment,HttpServletRequest request,Model model,HttpSession session) {
		System.out.println("Save Appointment");
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String email = messages.get(0);
		appointment.setPatientEmail(email);
		appointment.setIsPaid("no");
		appointment.setIsConfirmed("no");
		appointment.setPrescription("Not Added Yet");
		Appointment app = patientService.saveAppointment(appointment);
		
		@SuppressWarnings("unchecked")
		List<String> appId = (List<String>) request.getSession().getAttribute("APPOINTMENT_ID");
		if (appId == null) {
			appId = new ArrayList<>();
			request.getSession().setAttribute("APPOINTMENT_ID", appId);
		}
		
		appId.add(String.valueOf(app.getId()));
			request.getSession().setAttribute("APPOINTMENT_ID", appId);
		
		
		return "redirect:/makePayment";
		
	}
	
	@GetMapping("/appointments")
	public String appointments(Model model, HttpSession session) {

		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String email = messages.get(0);
		List<Appointment> appointments = patientService.getAllAppointments(email);
		model.addAttribute("appointments", appointments);
		return "patient/appointments";
	}
	
	@GetMapping("/cancelAppointment/{id}")
	public String cancelAppointment(Model model, HttpSession session, @PathVariable(name="id") Long id) {

		patientService.cancelAppointment(id);
		return "redirect:/patient";
	}
	
	@GetMapping("/makePayment")
	public String makePayment(Model model) {

		Payment payment = new Payment();
		
		model.addAttribute("payment", payment);
		

		return "patient/makepayment";
	}
	
	@PostMapping("/savePayment")
	public String savePayment(@ModelAttribute("payment") Payment payment,Model model,HttpSession session) {
		System.out.println("savePayment");
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		
		@SuppressWarnings("unchecked")
        List<String> appIds = (List<String>) session.getAttribute("APPOINTMENT_ID");


		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String email = messages.get(0);
		payment.setPatientEmail(email);
		payment.setAmount("500");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    Date date = new Date();  
	    String dat = formatter.format(date);
		payment.setDate(dat);
		payment.setIsValid("Pending");
		payment.setAppointmentId(appIds.get(0));
		patientService.savePayment(payment);
		
		return "redirect:/patient";
		
	}
	
	@GetMapping("/bills")
	public String bills(Model model, HttpSession session) {

		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String email = messages.get(0);
		List<Payment> bills = patientService.getAllPayments(email);
		model.addAttribute("bills", bills);
		model.addAttribute("size", bills.size());
		return "patient/bills";
	}
	
}
