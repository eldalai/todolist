package com.gl.todolist.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Size;





import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity(name="users")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotEmpty
	@Size(min=5, max=20)
	@Email
	private String name; //TODO: Email=username
	
	@NotEmpty
	@Size(min=5, max=8)
	private String password;
	
	@Valid
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="user_id")
	private List<Task> task = new ArrayList<Task>();
	
	private boolean stateUser;
	private String token;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	//cambio desde Json 1.9. En la version anterior, con Json 1.6, el JsonIgnore en getPassword
	//no tenía ningun efecto en setPassword. A partir de la 1.9 si se pone @JsonIgnore en getPassword
	//tambien ignora el setPassword
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Task> getTask() {
		return task;
	}
	
	public void setTask(List<Task> task) {
		this.task = task;
	}

	public boolean isStateUser() {
		return stateUser;
	}

	public void setStateUser(boolean stateUser) {
		this.stateUser = stateUser;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String toString() {
		return Objects.toStringHelper(this).add("id", id)
										   .add("name", name)
										   .omitNullValues()
										   .toString();
	}			
}
