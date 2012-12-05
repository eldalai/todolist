package com.gl.todolist.web.controllers.impl;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gl.todolist.domain.User;
import com.gl.todolist.services.IUserServices;
import com.gl.todolist.services.exceptions.UserException;
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
	public User login(String email, String password,  HttpSession session) throws UserException {
		User user;
		try {
			user = userServices.login(email, password);
		} catch (EntityNotFoundException e) {
			throw new SecurityException();
		}
		if( user != null )
		{
			GrantedAuthority[] authorities = new GrantedAuthorityImpl [1];
			authorities[0] = new GrantedAuthorityImpl("ROLE_USER");
			Authentication auth = new UsernamePasswordAuthenticationToken(email, password, authorities );
			SecurityContextHolder.getContext().setAuthentication(auth );
			session.setAttribute("user", user);
		}
		return user;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public User confirmation(String email, String token) throws UserException {
		User user = null;
		userServices.userConfirmation(email, token);
		return user;
	}
}
