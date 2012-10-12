package com.gl.todolist.web.controllers.impl;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gl.todolist.services.exceptions.UserException;

public class UserController extends BaseController{
	
	 @ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
	 @ExceptionHandler(value = UserException.class)
	 public void userExistException( UserException exception, HttpServletResponse response ) throws Throwable{
		 returnResponse(exception, response);
	 }

}
