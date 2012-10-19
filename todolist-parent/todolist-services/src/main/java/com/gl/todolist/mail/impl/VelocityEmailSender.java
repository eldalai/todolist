package com.gl.todolist.mail.impl;

import java.util.ArrayList;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.gl.todolist.domain.User;
import com.gl.todolist.mail.Sender;
import com.gl.todolist.services.impl.Constants;

@Component
public abstract class VelocityEmailSender implements Sender {
	
	protected final VelocityEngine velocityEngine;
	protected final JavaMailSender mailSender;
	@Autowired
    protected TaskExecutor taskExecutor;
	@Autowired
	protected String appUrl;
	@Autowired
	protected String emailFrom;

    protected class SendTask implements Runnable {

	    private MimeMessagePreparator preparator;
	
	  	public SendTask(MimeMessagePreparator preparator) {
		this.preparator = preparator;
	  	}
	
	  	public void run() {
	  		mailSender.send(preparator);
	  	}

    }
    
    public VelocityEmailSender(VelocityEngine velocityEngine, JavaMailSender mailSender) {
    	this.velocityEngine = velocityEngine;
    	this.mailSender = mailSender;	
    }
    
    protected String[] getToUsers( final Map<Object, Object> hTemplateVariables ){
		ArrayList<User>lista = (ArrayList<User>) hTemplateVariables.get(Constants.USER);
		String[] to = new String[ lista.size() ];
        int toInx = 0;
	    for (User user : lista) {
	  	  to[ toInx ] = user.getName();
	  	  toInx++;
	    }
	    hTemplateVariables.remove(Constants.USER);
	    return to;
    }
    
    protected String getToUser( final Map<Object, Object> hTemplateVariables ){
		User user = (User) hTemplateVariables.get(Constants.USER);
	    return user.getName();
    }

    protected void execute(MimeMessagePreparator preparator) {
		taskExecutor.execute( new SendTask(preparator) );
    }
    
}
