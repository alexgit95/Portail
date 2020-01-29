package com.alex.api.portail.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exc)
			throws IOException, ServletException {
		 
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		
		System.out.println("Failed : "+request.getRemoteAddr());
		response.sendRedirect("/login");

	}

}
