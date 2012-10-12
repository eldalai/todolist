package com.gl.todolist.services.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gl.todolist.domain.User;
import com.gl.todolist.services.IUserServices;
import com.gl.todolist.services.exceptions.MessagesExceptions;
import com.gl.todolist.services.exceptions.UserException;
import com.gl.todolist.services.repository.IUserRepository;

@Service
@Transactional
public class UserServices implements IUserServices{
	
	@Autowired
	IUserRepository userRepository;
	private static final Logger logger = LoggerFactory.getLogger(UserServices.class);
	
	public User saveUser(User user) throws UserException {
		if(userRepository.validateNameUser(user) != null){
			throw new UserException(MessagesExceptions.USER_EXISTS);
		}
		user= userRepository.saveUpdateUser(user);
		return user;
	}
	
	public User UpdateUser(User user) throws UserException {
		if(userRepository.validateNameUser(user) != null){
			if(user.getId().compareTo(userRepository.validateNameUser(user).getId())!=0)
				throw new UserException(MessagesExceptions.USER_EXISTS);
		}
		User objUser= userRepository.saveUpdateUser(user);
		return objUser;
	}

	public void deleteUser(Long id) throws EntityNotFoundException {
		User user = userRepository.find(id);
		userRepository.remove(user);
	}
	
	public List<User> listUser(){
		return userRepository.findUsers();
	}

	public User findUser(Long id) throws EntityNotFoundException {
		User user = userRepository.find(id);
		return user;
	}

}
