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
import com.gl.todolist.services.ITaskServices;
import com.gl.todolist.services.repository.ITaskRepository;

@Service
@Transactional
public class TaskServices implements ITaskServices{
	
	@Autowired
	ITaskRepository taskRepository;
	private static final Logger logger = LoggerFactory.getLogger(TaskServices.class);
	
	public Task saveUpdateTask(Task task){
		Task objTask= taskRepository.saveUpdateTask(task);
		return objTask;
	}

	public void deleteTask(Long id) throws EntityNotFoundException {
		Task task = taskRepository.find(id);
		taskRepository.remove(task);
	}
	
	public List<Task> listTask(User user){
		return taskRepository.findTasks(user);
	}

	public Task findTask(Long id) throws EntityNotFoundException {
		Task task = taskRepository.find(id);
		return task;
	}
		
}
