package com.gl.todolist.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.google.common.base.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity(name="tasks")
public class Task implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private String title;
	@NotNull
	private TaskStatus taskStatus;
	@NotNull
	private TaskType taskType;
	@Valid
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	private User user;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
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
	@JsonIgnore
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String toString() {
		return Objects.toStringHelper(this).add("id", id)
										   .add("title", title)
										   .omitNullValues()
										   .toString();
	}
	
}
