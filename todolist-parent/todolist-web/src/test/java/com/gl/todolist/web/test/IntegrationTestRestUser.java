package com.gl.todolist.web.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import com.gl.todolist.domain.User;

@ContextConfiguration(locations={"classpath:integration-test-context.xml"}) 
public class IntegrationTestRestUser extends AbstractTestNGSpringContextTests {

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
		}
	}	
	
}
