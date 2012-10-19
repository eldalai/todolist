package com.gl.todolist.services;

public interface IUtilServices {

	boolean emailValidator(final String hex);
	String generateToken(int length, String type); 
	
}
