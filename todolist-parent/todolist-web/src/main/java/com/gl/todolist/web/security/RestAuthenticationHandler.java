package com.gl.todolist.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class RestAuthenticationHandler implements
		AuthenticationSuccessHandler, AuthenticationFailureHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication auth) throws IOException,
			ServletException {
		response.setStatus(HttpStatus.OK.value());
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException) throws IOException,
			ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().write( authException.getMessage() );
	}

}
