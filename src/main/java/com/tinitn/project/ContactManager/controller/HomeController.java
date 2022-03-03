package com.tinitn.project.ContactManager.controller;

import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tinitn.project.ContactManager.dao.UserRepository;
import com.tinitn.project.ContactManager.entity.Registration;
import com.tinitn.project.ContactManager.entity.User;


@RestController
public class HomeController {
	
	@Autowired
	private EmailService service;
	
	Random random = new Random(1000);
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String home() {
		return "Welcome";
	}
	
	@PostMapping("/do_register")
	public User registerUser(@Valid @RequestBody Registration reg, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement) {
//		if(!agreement)
//			return new RuntimeException("Agree to the terms to proceed");
		User u = new User();
		u.setName(reg.getName());
		u.setEmail(reg.getEmail());
		u.setAbout(reg.getAbout());
		u.setRole("ROLE_USER");
		u.setEnabled(true);
		u.setPassword(passwordEncoder.encode(reg.getPassword()));
		u.setImageUrl("default.png");
		return userRepo.save(u);
	}
	
	@PostMapping("/forgot-password")
	public String sendOtp(@RequestParam("email") String email) {
		int otp = random.nextInt(999999);
		service.sendSimpleEmail(email, Integer.toString(otp), "OTP From Contact Manager");
		return "Sent OTP";
	}
	
	@PostMapping("/verify-otp")
	public ResponseEntity<?> verify(@RequestParam("email") String email, @RequestParam("otp") String otp, @RequestParam("notp") String notp) {
		int otp1 = Integer.valueOf(otp);
		int otp2 = Integer.valueOf(notp);
		if(otp1==otp2)
			return new ResponseEntity<>("OTP Matched",HttpStatus.OK);
		else
			return new ResponseEntity<>("OTP not matched",HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/change-password")
	public User change(@RequestParam("email") String email,@RequestParam("pass") String pass) {
		User u = userRepo.getUserByUserName(email);
		u.setPassword(pass);
		return u;
	}
}
