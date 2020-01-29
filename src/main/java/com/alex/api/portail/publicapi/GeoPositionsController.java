package com.alex.api.portail.publicapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
	
	
	@Value("${config.emplacement.fichiers}")
	private String cheminProperties;
	
	
	private String ipGeoPositions;
	private int portGeoPosition;
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<String> getAll() {
		System.out.println("GetAll");
		init();
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<String> response = restTemplate.getForEntity(generateUrl()+"/positions/", String.class);
		headers.setContentType(response.getHeaders().getContentType());
		return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addnow/{lattitude}/{longitude}", method = RequestMethod.POST)
	public ResponseEntity<String> addPosition(@PathVariable String lattitude, @PathVariable String longitude) {
		System.out.println("AddPosition");
		init();
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<String> response = restTemplate.postForEntity(generateUrl()+"/positions/addnow/"+lattitude+"/"+longitude, null, String.class);
		headers.setContentType(response.getHeaders().getContentType());
		return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
	}
	
	
	
	private void init() {
		if(StringUtils.isEmpty(ipGeoPositions)) {
			System.out.println("init geoposition");
			String cheminConfigPositions=cheminProperties+"configuration-portail.properties";
			File configFile= new File(cheminConfigPositions);
			if(configFile.exists()) {
			Properties props = new Properties();
				try {
					props.load(new FileInputStream(configFile));
					ipGeoPositions=props.getProperty("ip.geoposition");
					portGeoPosition = Integer.parseInt(props.getProperty("port.geoposition"));
					System.out.println("Url geoposition chargee :"+generateUrl()+" depuis "+cheminConfigPositions);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("Le fichier de configuration "+configFile+" n'existe pas");
			}
		}
	}
	
	private String generateUrl() {
		return "http://"+ipGeoPositions+":"+portGeoPosition;
	}
}
