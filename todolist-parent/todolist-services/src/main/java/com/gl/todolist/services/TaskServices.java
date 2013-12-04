package com.gl.todolist.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.gl.todolist.domain.Task;
import com.gl.todolist.domain.User;

public interface TaskServices {
	
	Task saveUpdateTask(Task task);
	void deleteTask(Long id) throws EntityNotFoundException;
	List<Task> listTask(User user);
	Task findTask(Long id) throws EntityNotFoundException;
	
}
