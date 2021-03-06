package com.gl.todolist.mail.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.gl.todolist.services.impl.Constants;

@Component
public class SendUser extends VelocityEmailSender {

	private static final Logger logger = LoggerFactory
			.getLogger(SendUser.class);

	@Autowired
	public SendUser(VelocityEngine velocityEngine, JavaMailSender mailSender) {
		super(velocityEngine, mailSender);
	}

	public void send(final SimpleMailMessage msg, 
                final Map<Object, Object> hTemplateVariables) {
		
		   MimeMessagePreparator preparator = new MimeMessagePreparator() {
		   
	       public void prepare(MimeMessage mimeMessage) throws Exception {

	    	  MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
	          message.setTo(getToUser(hTemplateVariables));
	          message.setSubject(msg.getSubject());
	          hTemplateVariables.put(Constants.URL, appUrl);
	          
	          Map<String,Object> expectedMap = convertMap(hTemplateVariables);
	          
	          String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, Constants.TEMPLATE_USER, 
	        		  													expectedMap);
	          logger.debug( "enviando confirmacion de registracion: \n" + body );
	          message.setText(body, true);
	       }

			private Map<String, Object> convertMap(Map<Object, Object> input) {
				Map<String, Object> answer = new HashMap<String, Object>();
				for( Object key : input.keySet()) {
					Object value = input.get(key);
					answer.put((String)key, value);
				}
				return answer;
			}
		
	   };
	   execute( preparator );

   }
}