package com.alex.api.portail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;

@SpringBootApplication

@PropertySource("file:/properties/configuration-portail.properties")
@PropertySource("file:/properties/configuration-portail-ssl.properties")
@PropertySource("file:/properties/configuration-portail-secrets.properties")
public class PortailApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortailApplication.class, args);
	}
	
	@Bean
	public TimedAspect timedAspect(MeterRegistry registry) {
	    return new TimedAspect(registry);
	}

}
