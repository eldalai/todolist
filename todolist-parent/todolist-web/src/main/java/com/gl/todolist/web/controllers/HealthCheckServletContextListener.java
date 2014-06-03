package com.gl.todolist.web.controllers;

import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;

public class HealthCheckServletContextListener extends HealthCheckServlet.ContextListener {

	private HealthCheckRegistry healthCheckRegistry;	
	
	@Override
	protected HealthCheckRegistry getHealthCheckRegistry() {
		return this.healthCheckRegistry;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String contextKey = "org.springframework.web.context.WebApplicationContext.ROOT";
		ApplicationContext appContext;
		
		appContext = (ApplicationContext) event.getServletContext().getAttribute(contextKey);
		
		this.healthCheckRegistry = appContext.getBean(HealthCheckRegistry.class);

		super.contextInitialized(event);
	}
	
}
