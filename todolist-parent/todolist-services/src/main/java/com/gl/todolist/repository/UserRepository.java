package com.gl.todolist.repository;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.gl.todolist.domain.User;

public interface UserRepository{
	
	User saveUpdateUser(User user);
	void remove(User user) throws EntityNotFoundException;
	User find(Long id) throws EntityNotFoundException;
	List<User> findUsers();
	User validateNameUser(User user);
	User find(String email, String password) throws EntityNotFoundException;
	User userByEmail(String email);

}
