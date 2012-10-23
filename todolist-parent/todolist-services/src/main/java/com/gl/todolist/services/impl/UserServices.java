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
import com.gl.todolist.services.IUserServices;
import com.gl.todolist.services.IUtilServices;
import com.gl.todolist.services.exceptions.MessagesExceptions;
import com.gl.todolist.services.exceptions.UserException;
import com.gl.todolist.services.repository.IUserRepository;

@Service
@Transactional
public class UserServices implements IUserServices{
	
	@Autowired
	IUserRepository userRepository;
	@Autowired
	SendUser sendUser;
	@Autowired
	IUtilServices utilServices;
	private static final Logger logger = LoggerFactory.getLogger(UserServices.class);
	
	public User saveUser(User user) throws UserException {
		
		if(!utilServices.emailValidator(user.getName())){
			throw new UserException(MessagesExceptions.INVALID_MAIL);
		}
		if(userRepository.validateNameUser(user) != null){
			throw new UserException(MessagesExceptions.USER_EXISTS);
		}
		user.setStateUser(false);
		user.setToken(utilServices.generateToken(50, null));

		user = userRepository.saveUpdateUser(user);
		sendMail(user);
		return user;
	}
	
	public User UpdateUser(User user) throws UserException {
		if(userRepository.validateNameUser(user) != null){
			if(user.getId().compareTo(userRepository.validateNameUser(user).getId())!=0)
				throw new UserException(MessagesExceptions.USER_EXISTS);
		}
		User objUser= userRepository.saveUpdateUser(user);
		return objUser;
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
		user = userRepository.userByEmail(email);
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
	
	public User login(String email, String password) throws UserException {
		User user = null;
		user = userRepository.find(email, password);
		if(user==null){
			throw new UserException(MessagesExceptions.USER_NOT_FOUND);
		}
		if(!user.isStateUser()){
			throw new UserException(MessagesExceptions.USER_INACTIVE);
		}
		return user;
	}

}
