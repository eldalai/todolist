package com.gl.todolist.services.exceptions;

public class UserException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public UserException(String msg) {
        super(msg);
    }
	
	public UserException(){
		
	}
}
