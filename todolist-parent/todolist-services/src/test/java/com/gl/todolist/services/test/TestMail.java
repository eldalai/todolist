package com.gl.todolist.services.test;

import org.junit.Test;

import com.dumbster.smtp.SimpleSmtpServer;
import com.gl.todolist.domain.User;

public class TestMail extends TestBase{

	@Test
	public void testEnviarMail() throws InterruptedException{

		//arranca el servidor de mail de  Dumbster.
        SimpleSmtpServer server = SimpleSmtpServer.start(62525);
		
		User user = new User();
		user.setName("pepito@hotmail.com");
 		user.setPassword("123");
		sendMail(user);
		Thread.sleep(5000);
		
		//detiene el server
		server.stop();
		assertTrue(server.getReceivedEmailSize() == 1);

	}
}
