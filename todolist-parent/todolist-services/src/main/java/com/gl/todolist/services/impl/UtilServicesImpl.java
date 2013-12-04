package com.gl.todolist.services.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.gl.todolist.services.UtilServices;

@Service
public class UtilServicesImpl implements UtilServices{
	
	private Pattern pattern;
	private Matcher matcher;
 
	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public boolean emailValidator(final String hex) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(hex);
		return matcher.matches();
	}
	
	public String generateToken(int length, String type) {
		String key = null;
		String result = "";

		if (type == null) {
			key = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		} else {
			if (type.equalsIgnoreCase("text"))
				key = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
			if (type.equalsIgnoreCase("number"))
				key = "0123456789";
			if (type.equalsIgnoreCase("specials"))
				key = "!�$%&/()=?�@''!";
		}

		for (int i = 0; i < length; i++) {
			result += (key.charAt((int) (Math.random() * key.length())));
		}
		return result;
	}
	
}
