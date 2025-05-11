package com.deepak.journalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.journalapp.entity.User;
import com.deepak.journalapp.service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/health-check")
	public String healthCheck() {
		return "Ok";
	}
	

	@PostMapping("/create-user")
	public void createUser(@RequestBody User user) {
		userService.createNewUser(user);
	}
	
}
