package com.gl.todolist.web.controllers.impl;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gl.todolist.domain.User;
import com.gl.todolist.web.controllers.IRestSessionServices;

@Controller
@RequestMapping("/session")
public class RestSessionServices extends UserController implements IRestSessionServices{

	public RestSessionServices() {
		super();
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public User login(String email, String password) throws EntityNotFoundException{
		
		return null;
//		return iUserServices.findUser(id);
	}
	
}
