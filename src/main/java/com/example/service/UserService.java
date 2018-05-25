package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.dao.UserRepo;
import com.example.model.User;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	public List<User> getUsers() {
		return userRepo.findAll();
	}
	
	public User findUserById(String id) {
		return userRepo.findUserById(id);
	}
}
