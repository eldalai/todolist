package com.gl.todolist.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.gl.todolist.domain.Task;

public interface ITaskServices {
	
	Task saveUpdateTask(Task task);
	void deleteTask(Long id) throws EntityNotFoundException;
	List<Task> listTask();
	Task findTask(Long id) throws EntityNotFoundException;
	
}
