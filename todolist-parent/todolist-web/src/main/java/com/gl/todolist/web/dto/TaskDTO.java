package com.gl.todolist.web.dto;

import com.gl.todolist.domain.TaskStatus;
import com.gl.todolist.domain.TaskType;

public class TaskDTO {
	
	private Long id;
	private String title;
	private TaskStatus taskStatus;
	private TaskType taskType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public TaskStatus getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}
	public TaskType getTaskType() {
		return taskType;
	}
	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}
}
