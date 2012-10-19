package com.gl.todolist.web.controllers.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gl.todolist.domain.Task;
import com.gl.todolist.domain.User;
import com.gl.todolist.services.ITaskServices;
import com.gl.todolist.services.IUserServices;
import com.gl.todolist.services.exceptions.UserException;
import com.gl.todolist.web.controllers.IRestTaskServices;
import com.gl.todolist.web.controllers.IRestUserServices;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/tasks")
public class RestTaskServices extends UserController implements IRestTaskServices{

	public RestTaskServices() {
		super();
	}

	@Autowired
	ITaskServices iTaskServices;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CREATED)
	public Task saveTask(@RequestBody Task task, HttpSession session) throws UserException{
		User user = (User)session.getAttribute("user");
		if(user!=null){
			task.setUser(user);	
		}		
		return iTaskServices.saveUpdateTask(task);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Task updateTask(@RequestBody @Valid Task task) throws UserException{
		return iTaskServices.saveUpdateTask(task);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void deleteTask(@PathVariable Long id) throws EntityNotFoundException{
		iTaskServices.deleteTask(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Task> listTasks(HttpSession session){
		User user = (User) session.getAttribute("user");
		if( user == null )
			throw new SecurityException("No user in session");
		return iTaskServices.listTask( user );
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Task findTask(@PathVariable Long id) throws EntityNotFoundException{
		return iTaskServices.findTask(id);
	}
	
}
