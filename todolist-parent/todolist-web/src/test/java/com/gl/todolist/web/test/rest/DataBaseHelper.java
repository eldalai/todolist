package com.gl.todolist.web.test.rest;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataBaseHelper {

	@Autowired
	protected DataSource dataSource;
	
	public void clearDB() {
		JdbcTemplate template = new JdbcTemplate(this.dataSource);
		
		template.execute("delete from tasks");
		template.execute("delete from users");			
	}
	
}
