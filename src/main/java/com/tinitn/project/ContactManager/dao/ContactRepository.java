package com.tinitn.project.ContactManager.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tinitn.project.ContactManager.entity.Contact;
import com.tinitn.project.ContactManager.entity.User;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
	
	@Query("from Contact as c where c.user.id =:userId")
	public List<Contact> findContactsByUser(@Param("userId") Integer userId);
	
	@Query("from Contact as c where c.user.id =:userId")
	public Page<Contact> findContactsPageByUser(@Param("userId") Integer userId, Pageable p);
	
	public List<Contact> findContactByNameContainingAndUser(String keywords,User user);
}
