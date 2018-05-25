package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/hibernate/users", method = RequestMethod.GET)
	public List<User> getUser() {
		return userService.getUsers();
	}
	
	@RequestMapping(value = "/hibernate/user", method = RequestMethod.GET)
	public User getUserByParamId(String id) {
		return userService.findUserById(id);
	}
	
	@RequestMapping(value = "/hibernate/user/{id}", method = RequestMethod.GET)
	public User getUserByPathId(@PathVariable String id) {
		return userService.findUserById(id);
	}
}
