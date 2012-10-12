package com.gl.todolist.web.controllers;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.gl.todolist.domain.User;
import com.gl.todolist.services.exceptions.UserException;


public interface IRestUserServices {
	
	User saveUser(User user) throws UserException;
	User updateUser(User user)throws UserException;
	void deleteUser(Long id)throws EntityNotFoundException;
	List<User> listUser();
	User findUser(Long id)throws EntityNotFoundException;

}
