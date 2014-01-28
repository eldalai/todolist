package com.gl.todolist.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gl.todolist.domain.User;
import com.gl.todolist.mail.impl.SendUser;
import com.gl.todolist.repository.UserRepository;
import com.gl.todolist.services.UserServices;
import com.gl.todolist.services.UtilServices;
import com.gl.todolist.services.exceptions.MessagesExceptions;
import com.gl.todolist.services.exceptions.UserException;

@Service
@Transactional
public class UserServicesImpl implements UserServices{
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	SendUser sendUser;
	@Autowired
	UtilServices utilServices;
	private static final Logger logger = LoggerFactory.getLogger(UserServicesImpl.class);
	
	public User saveUser(User user) throws UserException {
		
//		if(!utilServices.emailValidator(user.getName())){
//			throw new UserException(MessagesExceptions.INVALID_MAIL);
//		}
		if(userRepository.findByName(user.getName()) != null){
			throw new UserException(MessagesExceptions.USER_EXISTS);
		}
		user.setStateUser(true);
		// No molestar con mails...
		//user.setStateUser(false);
		//user.setToken(utilServices.generateToken(50, null));

		user = userRepository.saveUpdateUser(user);
//		sendMail(user);
		return user;
	}
	
	public User updateUser(User user) throws UserException {
	    User existingUser = userRepository.findByName(user.getName()); 
		if(existingUser != null){
			if(user.getId().compareTo(existingUser.getId())!=0)
				throw new UserException(MessagesExceptions.USER_EXISTS);
		}
		return userRepository.saveUpdateUser(user);
	}

	public void deleteUser(Long id) throws EntityNotFoundException {
		User user = userRepository.find(id);
		userRepository.remove(user);
	}
	
	public List<User> listUser(){
		return userRepository.findUsers();
	}

	public User findUser(Long id) throws EntityNotFoundException {
		User user = userRepository.find(id);
		return user;
	}

	@Override
	public User findUser(String email, String password)
			throws EntityNotFoundException {
		User user = userRepository.find(email, password);
		return user;
	}
	
	private void sendMail(User user){
		SimpleMailMessage msg = new SimpleMailMessage();
		Map<Object, Object> hTemplateVariables = new HashMap<Object, Object>();
		hTemplateVariables.put(Constants.USER, user);
		msg.setSubject(Constants.SUBJECT);
		sendUser.send(msg, hTemplateVariables);
	}
	
	public User userConfirmation( String email, String token )throws UserException{
		User user = null;
		user = userRepository.findByName(email);
		if(user==null){
			throw new UserException(MessagesExceptions.USER_NOT_FOUND);
		}
		if(!user.getToken().equalsIgnoreCase(token)){
			throw new UserException(MessagesExceptions.INVALID_TOKEN);
		}
		user.setStateUser(true);
		user = userRepository.saveUpdateUser(user);
		return user;
	}

}
