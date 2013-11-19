package com.gl.todolist.web.controllers;

import com.gl.todolist.domain.User;
import com.gl.todolist.services.exceptions.UserException;


public interface IRestSessionServices {

	User confirmation(String email, String token) throws UserException;
}
