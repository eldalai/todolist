package com.gl.todolist.services.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gl.todolist.domain.Task;
import com.gl.todolist.domain.User;
import com.gl.todolist.repository.ITaskRepository;
import com.gl.todolist.services.ITaskServices;

@Service
@Transactional
public class TaskServices implements ITaskServices{
	
	@Autowired
	ITaskRepository taskRepository;
	private static final Logger logger = LoggerFactory.getLogger(TaskServices.class);
	
	public Task saveUpdateTask(Task task){
		return taskRepository.saveUpdateTask(task);
	}

	public void deleteTask(Long id) throws EntityNotFoundException {
		taskRepository.remove(id);
	}
	
	public List<Task> listTask(User user){
		return taskRepository.findTasks(user);
	}

	public Task findTask(Long id) throws EntityNotFoundException {
		return taskRepository.find(id);
	}
		
}
