package com.alex.api.portail.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;



public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	@Autowired
	private MeterRegistry meterRegistry;
	private boolean init=false;
	private Counter mycounter;

   

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exc)
			throws IOException, ServletException {
		 
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		if(!init) {
			initOrderCounters();
		}
		mycounter.increment();
		System.out.println("Failed : "+request.getRemoteAddr());
		response.sendRedirect("/login");

	}
	
	
	private void initOrderCounters() {
	   mycounter = this.meterRegistry.counter("authent","type","failed_authent");
	   init=true;
	}

}
