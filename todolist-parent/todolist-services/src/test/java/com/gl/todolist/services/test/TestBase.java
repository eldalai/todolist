package com.gl.todolist.services.test;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gl.todolist.domain.Task;
import com.gl.todolist.domain.TaskStatus;
import com.gl.todolist.domain.TaskType;
import com.gl.todolist.domain.User;
import com.gl.todolist.services.ITaskServices;
import com.gl.todolist.services.IUserServices;
import com.gl.todolist.services.impl.UserServices;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class TestBase extends TestCase{
	
	private static InitialContext initialContext;
	private static final Logger logger = LoggerFactory.getLogger(TestBase.class);
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		try {
			// set the context factory
			System.setProperty("java.naming.factory.initial",
					"org.apache.xbean.spring.jndi.SpringInitialContextFactory");

			// initialize context. the object instance is not important, but in
			// the process, the context is loaded.
			initialContext = new InitialContext();

		} catch (NamingException ex) {
			logger.error("Cannot init test context.", ex);
		}
	}
	
	@Test
	public void testContext() {
		Assert.assertNotNull(initialContext);
	}

	@Autowired
	protected IUserServices iUserServices;
	@Autowired
	protected ITaskServices iTaskServices;
	
	protected User getUser(String name, String password, List<Task> tasks){
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setTask(tasks);
		return user;
	}
		
	protected Task getTask(TaskStatus taskStatus, TaskType taskType){
		Task task = new Task();
		task.setTaskStatus(taskStatus);
		task.setTaskType(taskType);
		return task;
	}
	
}
