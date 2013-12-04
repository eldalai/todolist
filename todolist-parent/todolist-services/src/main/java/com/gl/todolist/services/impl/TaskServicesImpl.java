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
import com.gl.todolist.repository.TaskRepository;
import com.gl.todolist.services.TaskServices;

@Service
@Transactional
public class TaskServicesImpl implements TaskServices{
	
	@Autowired
	TaskRepository taskRepository;
	private static final Logger logger = LoggerFactory.getLogger(TaskServicesImpl.class);
	
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
