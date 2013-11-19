package com.gl.todolist.web.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.gl.todolist.domain.User;
import com.gl.todolist.services.IUserServices;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	IUserServices userServices;
	
	@Override
	public boolean supports(Class<? extends Object> arg0) {
		return true;
	}

	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		String email = auth.getName();
		String password = (String) auth.getCredentials();
		User user = userServices.findUser(email, password);
		if( user == null )
		{
			throw new BadCredentialsException("Usuario o Password Invalido");
		} else {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			GrantedAuthority grantedAuthority = new GrantedAuthorityImpl("ROLE_USER");
			authorities.add( grantedAuthority  );
			UsernamePasswordAuthenticationToken authOk = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),authorities);
			authOk.setDetails(user);
			return authOk;
		}
	}


}
