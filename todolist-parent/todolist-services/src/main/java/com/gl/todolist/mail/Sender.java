package com.gl.todolist.mail;
import java.util.Map;

import org.springframework.mail.SimpleMailMessage;

public interface Sender {

	void send(final SimpleMailMessage msg, final Map<Object, Object> hTemplateVariables);
}
