package com.gl.todolist.web.controllers;

import com.gl.todolist.domain.User;
import com.gl.todolist.web.controllers.impl.Login;


public interface IRestSessionServices {
	//
	User login(String user, String password) throws Exception;


}
