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
@RequestMapping("/api/v1/compte")
public class CompteController {
	
	private RestTemplate restTemplate = new RestTemplate();
	
	
	
	@Value("${ip.compte}")
	private String ipCompte;
	@Value("${port.compte}")
	private int portCompte;
	

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ResponseEntity<String> getCategories() {
		System.out.println("getCategories");
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<String> response = restTemplate.getForEntity(generateUrl()+"/api/v1/compte/categories", String.class);
		headers.setContentType(response.getHeaders().getContentType());
		return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/depense/{categorie}", method = RequestMethod.GET)
	public ResponseEntity<String> getCurrentDepense(@PathVariable String categorie) {
		System.out.println("getCurrentDepense");
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<String> response = restTemplate.getForEntity(generateUrl()+"/api/v1/compte/depense/"+categorie, String.class);
		headers.setContentType(response.getHeaders().getContentType());
		return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/depense/{montant}/{categorie}", method = RequestMethod.POST)
	public ResponseEntity<String> addDepense(@PathVariable String montant, @PathVariable String categorie) {
		System.out.println("getCurrentDepense");
		
		HttpHeaders headers = new HttpHeaders();
		
		ResponseEntity<String> response = restTemplate.postForEntity(generateUrl()+"/api/v1/compte/depense/"+montant+"/"+categorie, null, String.class);
		headers.setContentType(response.getHeaders().getContentType());
		return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
	}
	
	
	private String generateUrl() {
		return "http://"+ipCompte+":"+portCompte;
	}
}
