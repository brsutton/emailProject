package com.sutton.email.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sutton.email.pop3server.Pop3Server;
import com.sutton.email.service.UserService;

import model.User;

@RestController
public class EmailController {
	@Autowired
	Pop3Server pop3Server;
	@Autowired
	UserService userService;
	
	@CrossOrigin("*")
	@RequestMapping("/emails")
	public String getEmails() {
		int result = pop3Server.checkMail();
		if(result == 1) {
			return "added";
		}
		return "failure";
	}
	@CrossOrigin("*")
	@RequestMapping("/users")
	
	public List<User> getUsers(){
		return userService.getUsers();
	}
	
}
