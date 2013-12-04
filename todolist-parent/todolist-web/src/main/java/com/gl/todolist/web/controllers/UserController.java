package com.gl.todolist.web.controllers;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gl.todolist.domain.User;
import com.gl.todolist.services.UserServices;
import com.gl.todolist.services.exceptions.UserException;


@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

	public UserController() {
		super();
	}

	@Autowired
	UserServices userServices;

	@RequestMapping(method = RequestMethod.POST, consumes="application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CREATED)
	public User saveUser(@RequestBody User user) throws UserException{
		return userServices.saveUser(user);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public User updateUser(@RequestBody @Valid User user) throws UserException{
		return userServices.updateUser(user);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void deleteUser(@PathVariable Long id) throws EntityNotFoundException{
		userServices.deleteUser(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<User> listUser(){
		return userServices.listUser();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public User findUser(@PathVariable Long id) throws EntityNotFoundException{
		return userServices.findUser(id);
	}
	
}
