package com.gl.todolist.web.controllers;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import com.gl.todolist.domain.Task;
import com.gl.todolist.services.exceptions.UserException;


public interface IRestTaskServices {
	
	Task saveTask(Task task) throws UserException;
	Task updateTask(Task task)throws UserException;
	void deleteTask(Long id)throws EntityNotFoundException;
	List<Task> listTasks(HttpSession session);
	Task findTask(Long id)throws EntityNotFoundException;

}
