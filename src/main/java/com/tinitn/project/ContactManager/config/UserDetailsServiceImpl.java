package com.tinitn.project.ContactManager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tinitn.project.ContactManager.dao.UserRepository;
import com.tinitn.project.ContactManager.entity.User;

public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    User user = userRepo.getUserByUserName(username);
	    if(user==null)
	    	throw new UsernameNotFoundException(username);
	    CustomUserDetails customUserDetails = new CustomUserDetails(user);
		return customUserDetails;
	}

}
