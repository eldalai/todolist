package com.gl.todolist.web.controllers;

import java.io.PrintWriter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gl.todolist.services.exceptions.UserException;

public class BaseController {
	
	 @ResponseStatus(value=HttpStatus.FORBIDDEN)
	 @ExceptionHandler(value = SecurityException.class)
	 public void securityException( Exception exception, HttpServletResponse response ) throws Throwable{
		 returnResponse(exception, response);
	 }
	 
	 @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	 @ExceptionHandler(value = Exception.class)
	 public void serverException( Exception exception, HttpServletResponse response ) throws Throwable{
		 returnResponse(exception, response);
	 }
	 
	 @ResponseStatus(value=HttpStatus.NOT_FOUND)
	 @ExceptionHandler(value = EntityNotFoundException.class)
	 public void entityNotFoundExcepcion( EntityNotFoundException exception, HttpServletResponse response ) throws Throwable{
		 returnResponse(exception, response);
	 }

	 @ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
	 @ExceptionHandler(value = UserException.class)
	 public void userExistException( UserException exception, HttpServletResponse response ) throws Throwable{
		 returnResponse(exception, response);
	 }
	 
	 
	 protected void returnResponse( Exception exception, HttpServletResponse response ) throws Throwable{
		 response.setHeader( exception.getMessage(), exception.getClass().getName());
		 PrintWriter writer;
		 writer = response.getWriter();
		 writer.print( exception.getMessage() );
	 }
	
}
