package com.gl.todolist.web.test;

import javax.activation.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.gl.todolist.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:integration-test-context.xml"}) 
public class IntegrationTestRestUser extends TestCase {

	private static final String LOCAL_URL = "http://localhost:9090/todolist-web/rest/";
	private static final String LIST_USERS = LOCAL_URL + "users";


	
	@Autowired
	public RestTemplate restTemplate;
	
	@Test
	public void testCreateUser(){
		User objUser = new User();
		objUser.setName("arm.rosario@gmail.com");
		objUser.setPassword("124567");
		try{
			User user = restTemplate.postForObject(LIST_USERS, objUser, User.class, new Object[]{});
			
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}	
	
}
