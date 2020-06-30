package com.example.circuitbreakerreading;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class BookService {

    public static final String OTRO_MICROSERVICE_DE_CONSULTA = "http://localhost:8090/recommended";
    private final RestTemplate restTemplate;

  public BookService(RestTemplate rest) {
	this.restTemplate = rest;
  }

  @HystrixCommand(fallbackMethod = "reliable")
  public String readingList() {
	URI uri = URI.create(OTRO_MICROSERVICE_DE_CONSULTA);

	return this.restTemplate.getForObject(uri, String.class);
  }

  public String reliable() {
	return "Cloud Native Java (O'Reilly)";
  }

}
