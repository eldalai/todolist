package com.gl.todolist.services.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gl.todolist.domain.Task;
import com.gl.todolist.domain.TaskStatus;
import com.gl.todolist.domain.TaskType;
import com.gl.todolist.domain.User;
import com.gl.todolist.services.exceptions.UserException;

public class TestUser extends TestBase{
		
	@Test
	public void testCreateUser(){
		
		List<Task> tasks = new ArrayList<Task>();
		
		Task task1 = getTask(TaskStatus.PENDING, TaskType.NORMAL);
		Task task2 = getTask(TaskStatus.PENDING, TaskType.URGENT);
		Task task3 = getTask(TaskStatus.DONE, TaskType.NORMAL);
		
		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task3);
		
		User user  = getUser("Juan.Perez@gmail.com", "123456", tasks);
		
		try {
			User user2 = iUserServices.saveUser(user);
			assertEquals(user.getName(), user2.getName());
		} catch (UserException e) {
			//assertEquals(Constants.CONFIRMACION_ERROR, confirmacion.isEstado());
		} catch(Exception e){
			
		}
		
		List<Task> tasks2 = new ArrayList<Task>();
		
		Task task4 = getTask(TaskStatus.PENDING, TaskType.NORMAL);
		Task task5 = getTask(TaskStatus.PENDING, TaskType.URGENT);
		Task task6 = getTask(TaskStatus.DONE, TaskType.NORMAL);
		
		tasks2.add(task4);
		tasks2.add(task5);
		tasks2.add(task6);
		
		User user3  = getUser("Juan.Perez@gmail.com", "8911001", tasks2);
		
		try {
			User user4 = iUserServices.saveUser(user3);
			assertEquals(user3.getName(), user4.getName());
		} catch (UserException e) {
			//assertEquals(Constants.CONFIRMACION_ERROR, confirmacion.isEstado());
		} catch(Exception e){
			
		}
			
	}
			
	
}
