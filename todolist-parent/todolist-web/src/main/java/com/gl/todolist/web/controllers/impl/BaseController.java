package com.gl.todolist.web.controllers.impl;

import java.io.PrintWriter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BaseController {
	
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

	 protected void returnResponse( Exception exception, HttpServletResponse response ) throws Throwable{
		 response.setHeader( exception.getMessage(), exception.getClass().getName());
		 PrintWriter writer;
		 writer = response.getWriter();
		 writer.print( exception.getMessage() );
	 }
	
}
