package com.tinitn.project.ContactManager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tinitn.project.ContactManager.dao.ContactRepository;
import com.tinitn.project.ContactManager.dao.UserRepository;
import com.tinitn.project.ContactManager.entity.Contact;
import com.tinitn.project.ContactManager.entity.Payload;
import com.tinitn.project.ContactManager.entity.User;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ContactRepository contactRepo;
	
//	private final Path root = Paths.get("img");
	
	private static String UPLOAD_FOLDER = "C://STS//";
	
	@GetMapping("/index")
	public String dashboard(Principal principal) {
		String username = principal.getName();
		User u = userRepo.getUserByUserName(username);
		return "Welcome user"+" "+u.getName();
	}
	
	@GetMapping("/getUser/{id}")
	public User getUser(@PathVariable("id") Integer id) {
		return userRepo.findById(id).get();
	}
	
	@PostMapping(value = "/process-contact")
	public Contact processContact(@ModelAttribute Payload payload, Principal principal) {
		Contact contact = new Contact();
		contact.setName(payload.getName());
		contact.setSecondName(payload.getSecondName());
		contact.setEmail(payload.getEmail());
		contact.setWork(payload.getWork());
		contact.setDescription(payload.getDescription());
		contact.setPhone(payload.getPhone());
		try {
		String name = principal.getName();
		User u = userRepo.getUserByUserName(name);
		contact.setUser(u);
		if(payload.getProfileImage()==null) {
			System.out.println("No file chosen");
		}
		else {
			contact.setImage(payload.getProfileImage().getOriginalFilename());
//			File saveFile = new ClassPathResource("static/img").getFile();
//			Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+payload.getProfileImage().getOriginalFilename());
//			Files.copy(payload.getProfileImage().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
//			Files.copy(payload.getProfileImage().getInputStream(), this.root.resolve(payload.getProfileImage().getOriginalFilename()));
			
			byte[] bytes = payload.getProfileImage().getBytes();
			Path path = Paths.get(UPLOAD_FOLDER + payload.getProfileImage().getOriginalFilename());
			Files.write(path, bytes);
		}
		u.getContacts().add(contact);
		this.userRepo.save(u);
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: "+e.getMessage());
			e.printStackTrace();
		}
		contactRepo.save(contact);
		return contact;
	}
}
