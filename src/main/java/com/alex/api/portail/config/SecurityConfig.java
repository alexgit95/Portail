package com.alex.api.portail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private int nbJourRememberMe;

	@Value("${secure.admin.login}")
	private String adminLogin;
	@Value("${secure.admin.password}")
	private String adminPassword;
	@Value("${secure.secret.rememberme}")
	private String secretRememberMe;
	@Value("${secure.guest.login}")
	private String guestLogin;
	@Value("${secure.guest.password}")
	private String guestPassword;

	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser(adminLogin).password(passwordEncoder().encode(adminPassword))
				.roles("USER", "ADMIN").and().withUser(guestLogin).password(passwordEncoder().encode(guestPassword))
				.roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers("/positions/**").hasRole("USER")
		.antMatchers("/myphotos/**").hasRole("USER")
		.antMatchers("/api/v1/positions/**").hasRole("USER")
		.antMatchers("/api/v1/photos/**").hasRole("USER")
		.antMatchers("/actuator/**").hasRole("ADMIN")
		.antMatchers("/compta/**").permitAll()
		.antMatchers("/api/v1/compte/**").permitAll()
		.antMatchers("/*").permitAll()
		.antMatchers("/vendor/**").permitAll()
		.antMatchers("/css/*").permitAll()
		.antMatchers("/js/*").permitAll()
		.antMatchers("/img/*").permitAll()
		.anyRequest().authenticated().and().formLogin().loginProcessingUrl("/perform_login")
				.failureHandler(customAuthenticationFailureHandler()).and().logout().deleteCookies("JSESSIONID").and()
				.rememberMe().key(secretRememberMe).tokenValiditySeconds(60 * 60 * 24 * nbJourRememberMe);
		;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	public AuthenticationFailureHandler customAuthenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}
}
