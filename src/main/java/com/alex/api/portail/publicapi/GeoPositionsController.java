package com.alex.api.portail.publicapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/positions")
public class GeoPositionsController {
	
	private RestTemplate restTemplate = new RestTemplate();
	
	
	
	@Value("${ip.geoposition}")
	private String ipGeoPositions;
	@Value("${port.geoposition}")
	private int portGeoPosition;
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<String> getAll() {
		System.out.println("GetAll");
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<String> response = restTemplate.getForEntity(generateUrl()+"/positions/", String.class);
		headers.setContentType(response.getHeaders().getContentType());
		return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addnow/{lattitude}/{longitude}", method = RequestMethod.POST)
	public ResponseEntity<String> addPosition(@PathVariable String lattitude, @PathVariable String longitude) {
		System.out.println("AddPosition");
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<String> response = restTemplate.postForEntity(generateUrl()+"/positions/addnow/"+lattitude+"/"+longitude, null, String.class);
		headers.setContentType(response.getHeaders().getContentType());
		return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
	}
	
	private String generateUrl() {
		return "http://"+ipGeoPositions+":"+portGeoPosition;
	}
}
