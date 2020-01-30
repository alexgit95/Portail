package com.alex.api.portail.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class HealthPositionsIndicator implements HealthIndicator {

	@Value("${ip.geoposition}")
	private String ipGeoPositions;
	@Value("${port.geoposition}")
	private int portGeoPosition;

	@Override
	public Health health() {
		RestTemplate restTemplate = new RestTemplate();
		try {
			restTemplate.getForEntity("http://" + ipGeoPositions + ":" + portGeoPosition + "/positions/", String.class);
		} catch (Exception e) {
			return Health.down().withDetail("Erreur lors de l'appel", e.getMessage()).build();
		}
		return Health.up().build();
	}

}
