package com.alex.api.portail.publicapi;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/photos")
public class PhotosController {

	private RestTemplate restTemplate = new RestTemplate();

	
	@Value("${ip.myphotos}")
	private String ipMyPhotos;
	@Value("${port.myphotos}")
	private int portMyPhotos;
	
	
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<String> getAll(HttpServletRequest request) {
		System.out.println("GetAll");
		return forwardRequest(request);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ResponseEntity<String> create(HttpServletRequest request) {
		System.out.println("create");
		return forwardRequest(request);
		
	}
	
	@RequestMapping(value = "/telephone", method = RequestMethod.GET)
	public ResponseEntity<String> telephone(HttpServletRequest request) {
		System.out.println("telephone");
		return forwardRequest(request);
		
	}
	
	@RequestMapping(value = "/serveur", method = RequestMethod.GET)
	public ResponseEntity<String> serveur(HttpServletRequest request) {
		System.out.println("serveur");
		return forwardRequest(request);
		
	}
	
	@RequestMapping(value = "/hubic", method = RequestMethod.GET)
	public ResponseEntity<String> gubiv(HttpServletRequest request) {
		System.out.println("hubic");
		return forwardRequest(request);
		
	}
	
	@RequestMapping(value = "/glacier", method = RequestMethod.GET)
	public ResponseEntity<String> glacier(HttpServletRequest request) {
		System.out.println("glacier");
		return forwardRequest(request);
		
	}

	private ResponseEntity<String> forwardRequest(HttpServletRequest request) {
		String forwardURL = generateUrl()+generateForwardURL(request.getRequestURI(), request.getParameterMap());
		System.out.println("Forward URL : "+forwardURL);			
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<String> response = restTemplate.getForEntity(forwardURL, String.class);
		headers.setContentType(response.getHeaders().getContentType());
		return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
	}
	
	
	private String generateForwardURL(String url, Map<String, String[]> parameterMap) {

		boolean firstParam = true;

		url = url.replaceAll("/api/v1/photos", "");
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			if(firstParam) {
				url+="?"+entry.getKey()+"="+entry.getValue()[0];
				firstParam=false;
			}else {
				url+="&"+entry.getKey()+"="+entry.getValue()[0];
			}
		}
		
		return url;
	}
	
	
	
	
	private String generateUrl() {
		return "http://"+ipMyPhotos+":"+portMyPhotos;
	}
}
