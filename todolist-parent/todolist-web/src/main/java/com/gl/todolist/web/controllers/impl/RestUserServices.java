package com.gl.todolist.web.controllers.impl;

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
import com.gl.todolist.services.IUserServices;
import com.gl.todolist.services.exceptions.UserException;
import com.gl.todolist.web.controllers.IRestUserServices;

@Controller
@RequestMapping("/users")
public class RestUserServices extends UserController implements IRestUserServices{

	public RestUserServices() {
		super();
	}

	@Autowired
	IUserServices iUserServices;

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes="application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CREATED)
	public User saveUser(@RequestBody User user) throws UserException{
		return iUserServices.saveUser(user);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public User updateUser(@RequestBody @Valid User user) throws UserException{
		return iUserServices.UpdateUser(user);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void deleteUser(@PathVariable Long id) throws EntityNotFoundException{
		iUserServices.deleteUser(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<User> listUser(){
		return iUserServices.listUser();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public User findUser(@PathVariable Long id) throws EntityNotFoundException{
		return iUserServices.findUser(id);
	}
	
}
