package com.gl.todolist.web.test.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.gl.todolist.domain.User;

public class JacksonTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String json = "[{"id":49,"name":"pepito1@gmail.com","task":[],"stateUser":true,"token":null},{"id":50,"name":"pepito2@gmail.com","task":[],"stateUser":true,"token":null}]"
//		// TODO Auto-generated method stub

	}

	
	// ******************  otras pruebas hechas con Jackson en el camino *************
	/**
	 * Este mecanismo no funcionón porque no podía sigue ignorando la anotacion 
	 * @JsonIgnore que tiene la property password y cuando estoy en el test quiero 
	 * que se serilice la propiedad. Para que funcione necesito poner la siguiente
	 * anotacion en la clase User
	 * 
	 *  @JsonFilter("userTestFilter")	
	 */
	private String createUserJsonUsingFilterOut() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		BeanPropertyFilter filter;
		FilterProvider filterProvider;
		String answer;
		
		filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name",  "password");
		filterProvider = new SimpleFilterProvider().addFilter("userTestFilter", filter);    	    														   
		answer = mapper.writer(filterProvider).writeValueAsString(createUser());
		
		return answer;
	}

	private User createUser() {
		User user = new User();
    	user.setName("pepito@gmail.com");
    	user.setPassword("zaraza");
		return user;
	}
		
	
}
