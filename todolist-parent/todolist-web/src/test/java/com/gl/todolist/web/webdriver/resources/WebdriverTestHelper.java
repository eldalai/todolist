package com.gl.todolist.web.webdriver.resources;


public class WebdriverTestHelper {

	public String generateString(int length, String type) {
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
	
