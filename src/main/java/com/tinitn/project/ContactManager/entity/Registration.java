package com.tinitn.project.ContactManager.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Registration {
	
	@NotBlank(message = "Name field is required")
	@Size(min = 2,max = 20,message = "Min is 2 and max is 20")
	private String name;
	private String email;
	private String password;
	private String about;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
	
}
