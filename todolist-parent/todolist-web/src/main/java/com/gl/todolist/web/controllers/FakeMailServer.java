package com.gl.todolist.web.controllers;

import com.dumbster.smtp.SimpleSmtpServer;

public class FakeMailServer {

	private SimpleSmtpServer server;

	public FakeMailServer() {
		super();
		setServer(SimpleSmtpServer.start(62525));
	}

	public SimpleSmtpServer getServer() {
		return server;
	}

	public void setServer(SimpleSmtpServer server) {
		this.server = server;
	}
	
}
