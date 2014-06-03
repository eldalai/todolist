package com.gl.todolist.web.controllers;

import java.util.Enumeration;

import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.AdminServletContextListener;
import com.codahale.metrics.servlets.MetricsServlet;

public class MetricsServletContextListener extends MetricsServlet.ContextListener {

	private MetricRegistry registry;
	
	@Override
	protected MetricRegistry getMetricRegistry() {
		return this.registry;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String contextKey = "org.springframework.web.context.WebApplicationContext.ROOT";
		ApplicationContext appContext;
		
		appContext = (ApplicationContext) event.getServletContext().getAttribute(contextKey);
		
		this.registry = appContext.getBean(MetricRegistry.class);

		super.contextInitialized(event);
	}
}
