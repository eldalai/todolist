package com.gl.todolist.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.gl.todolist.domain.User;
import com.gl.todolist.services.exceptions.UserException;

public interface UserServices {
	
	User saveUser(User user) throws UserException;
	User updateUser(User user) throws UserException;
	void deleteUser(Long id) throws EntityNotFoundException;
	List<User> listUser();
	User findUser(Long id) throws EntityNotFoundException;
	User findUser(String email, String password) throws EntityNotFoundException;
	User userConfirmation( String email, String token)throws UserException;
	User login(String email, String password) throws UserException;
	
}
