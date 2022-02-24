package com.tinitn.project.ContactManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	private UserRepository userRepo;
	
	@GetMapping("/")
	public String home() {
		return "Welcome";
	}
	
	@PostMapping("/do_register")
	public User registerUser(@RequestBody Registration reg, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement) {
//		if(!agreement)
//			return new RuntimeException("Agree to the terms to proceed");
		User u = new User();
		u.setName(reg.getName());
		u.setEmail(reg.getEmail());
		u.setAbout(reg.getAbout());
		u.setRole("ROLE_USER");
		u.setEnabled(true);
		u.setPassword(reg.getPassword());
		u.setImageUrl("default.png");
		return userRepo.save(u);
	}
}