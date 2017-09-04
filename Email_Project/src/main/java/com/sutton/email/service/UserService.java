package com.sutton.email.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sutton.email.dao.UserDao;

import model.User;
@Service
public class UserService {
	@Autowired
	UserDao userDao;
	
	public int addUser(User user) {
		return userDao.insertUser(user);
	}
	
	public List<User> getUsers(){
		return userDao.getUsers();
	}
	
}
