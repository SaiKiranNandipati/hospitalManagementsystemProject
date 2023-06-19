package com.hms.app.controller;

import java.util.ArrayList;
import java.util.Base64;
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

import com.hms.app.model.Doctor;
import com.hms.app.model.Patient;
import com.hms.app.service.AdminService;
import com.hms.app.service.PatientService;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("/admin")
	public String getAdminWelcomePage(Model model, HttpSession session) {

		return "admin/welcomeadmin";
	}

	@GetMapping("/adddoctor")
	public String addDoctor(Model model) {

		Doctor doctor = new Doctor();
		model.addAttribute("doctor", doctor);
		return "admin/adddoctor";
	}

	@PostMapping("/saveDoctor")
	public String saveDoctor(@ModelAttribute("doctor") Doctor doctor, Model model) {
		System.out.println("Save Doctor");
		adminService.saveDoctor(doctor);

		return "redirect:/admin";

	}

	@GetMapping("/viewdoctor")
	public String viewdoctor(Model model) {

		List<Doctor> doctors = adminService.getAllDoctors();
		model.addAttribute("doctors", doctors);
		return "admin/viewdoctor";
	}

	@GetMapping("/editdoctor/{id}")
	public String editDoctor(Model model, HttpSession session, @PathVariable(name = "id") Long id) {

		Doctor doctor = adminService.getDoctorById(id);

		model.addAttribute("doctor", doctor);

		return "admin/editdoctor";
	}
