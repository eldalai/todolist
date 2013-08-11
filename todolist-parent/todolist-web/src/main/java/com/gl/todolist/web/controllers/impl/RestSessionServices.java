package com.gl.todolist.web.controllers.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

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
	public void login(@RequestBody Login loginInfo, HttpSession session) throws UserException {
		User user;
		try {
			user = userServices.login(loginInfo.getEmail(), loginInfo.getPassword());
		} catch (EntityNotFoundException e) {
			throw new SecurityException();
		}
		if (user != null) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>() ;
			authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
			Authentication auth = new UsernamePasswordAuthenticationToken(
					loginInfo.getEmail(), loginInfo.getPassword(), authorities);
			SecurityContextHolder.getContext().setAuthentication(auth);
			session.setAttribute("user", user);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void logout(HttpSession session) throws UserException {
		SecurityContextHolder.getContext().setAuthentication(null);
		session.removeAttribute("user");
		session.invalidate();
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
