package com.tinitn.project.ContactManager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tinitn.project.ContactManager.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
