package com.alex.api.portail.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

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

	private String adminLogin;

	private String adminPassword;

	private String secretRememberMe;

	private String guestLogin;

	private String guestPassword;

	@Value("${config.emplacement.fichiers}")
	private String cheminProperties;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("init security tokens");
		String cheminConfigPositions = cheminProperties + "configuration-portail-secrets.properties";
		File configFile = new File(cheminConfigPositions);
		if (configFile.exists()) {
			Properties props = new Properties();

			props.load(new FileInputStream(configFile));

			adminLogin = props.getProperty("secure.admin.login");
			adminPassword = props.getProperty("secure.admin.password");
			secretRememberMe = props.getProperty("secure.secret.rememberme");
			guestLogin = props.getProperty("secure.guest.login");
			guestPassword = props.getProperty("secure.guest.password");
			nbJourRememberMe = Integer.parseInt(props.getProperty("secure.nbjour.rememberme"));

			System.out.println("Chargement des tokens d'authent depuis " + cheminConfigPositions);

		} else {
			System.out.println("Le fichier de configuration " + configFile + " n'existe pas");
			System.exit(-1);
		}

		auth.inMemoryAuthentication().withUser(adminLogin).password(passwordEncoder().encode(adminPassword))
				.roles("USER", "ADMIN").and().withUser(guestLogin).password(passwordEncoder().encode(guestPassword))
				.roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/positions/**").hasRole("USER").antMatchers("/*")
				.permitAll().antMatchers("/css/*").permitAll().antMatchers("/js/*").permitAll().antMatchers("/img/*")
				.permitAll().anyRequest().authenticated().and().formLogin().loginProcessingUrl("/perform_login")
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
