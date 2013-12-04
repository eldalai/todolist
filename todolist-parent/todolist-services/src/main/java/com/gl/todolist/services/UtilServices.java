package com.gl.todolist.services;

public interface UtilServices {

	boolean emailValidator(final String hex);
	String generateToken(int length, String type); 
	
}
