package com.gl.todolist.repository;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.gl.todolist.domain.Task;
import com.gl.todolist.domain.User;

public interface ITaskRepository {
	
	Task saveUpdateTask(Task task);
	void remove(Long id) throws EntityNotFoundException;
	Task find(Long id) throws EntityNotFoundException;
	List<Task> findTasks(User user);
	
}
