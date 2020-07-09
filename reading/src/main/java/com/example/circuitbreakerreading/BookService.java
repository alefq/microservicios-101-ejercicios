package com.example.circuitbreakerreading;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RefreshScope
public class BookService {

    public static final String OTRO_MICROSERVICE_DE_CONSULTA = "http://localhost:8090/recommended";
    private final RestTemplate restTemplate;
    Logger LOGGER = LoggerFactory.getLogger(BookService.class);

    @Value("${bookstore.url:http://localhost:8090/recommended}")
    private String bookstoreUrl;

    public BookService(RestTemplate rest) {
        this.restTemplate = rest;
    }

    @HystrixCommand(fallbackMethod = "reliable")
    public String readingList() {
        URI uri = URI.create(OTRO_MICROSERVICE_DE_CONSULTA);
        if (bookstoreUrl != null) {
            uri = URI.create(bookstoreUrl);
        }
        LOGGER.info("Consultando servicio:  {}", uri);
        return this.restTemplate.getForObject(uri, String.class);
    }

    public String reliable() {
        return "Cloud Native Java (O'Reilly)";
    }

}
