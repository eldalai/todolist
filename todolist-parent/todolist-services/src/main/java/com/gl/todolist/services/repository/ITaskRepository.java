package com.gl.todolist.services.repository;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.gl.todolist.domain.Task;

public interface ITaskRepository {
	
	Task saveUpdateTask(Task task);
	void remove(Task task) throws EntityNotFoundException;
	Task find(Long id) throws EntityNotFoundException;
	List<Task> findTasks();
	
}
