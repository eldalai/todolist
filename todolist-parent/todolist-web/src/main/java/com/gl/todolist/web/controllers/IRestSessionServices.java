package com.gl.todolist.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gl.todolist.domain.User;
import com.gl.todolist.services.exceptions.UserException;
import com.gl.todolist.web.controllers.impl.Login;


public interface IRestSessionServices {
	//
	User login(String user, String password,  HttpSession session) throws UserException;
	User confirmation(String email, String token) throws UserException;
}
