package com.micro.ServiceA.controller;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/a")
public class ServiceAController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String BASE_URL = "https://localhost:8081/";

	private static final String SERVICE_A = "serviceA";

	int count=1;
	
	@GetMapping()
	//@CircuitBreaker(name="SERVICE_A" ,fallbackMethod="serviceAFallback")
	@Retry(name="SERVICE_A")
	public String serviceA() {
		System.out.println("Retry method called " + count++ + " times at " + new Date());
		return restTemplate.getForObject(BASE_URL + "b", String.class);
	}



	public String serviceAFallback(Exception e)
	{
		return "this ia a fallback method for serviceA";
	}
}
