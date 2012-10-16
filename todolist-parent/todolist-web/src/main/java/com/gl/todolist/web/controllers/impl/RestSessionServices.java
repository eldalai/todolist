package com.gl.todolist.web.controllers.impl;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gl.todolist.domain.User;
import com.gl.todolist.services.IUserServices;
import com.gl.todolist.web.controllers.IRestSessionServices;

@Controller
@RequestMapping("/session")
public class RestSessionServices extends UserController implements IRestSessionServices{

	@Autowired
	IUserServices userServices;
	
	public RestSessionServices() {
		super();
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public User login(String email, String password,  HttpSession session, HttpServletRequest request) {
		User user;
		try {
			user = userServices.findUser(email, password);
		} catch (EntityNotFoundException e) {
			throw new SecurityException();
		}
		if( user != null )
			session.setAttribute("user", user);
		return user;
	}
	
}
