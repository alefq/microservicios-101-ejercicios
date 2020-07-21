package com.example.circuitbreakerbookstore;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class CircuitBreakerBookstoreApplication {

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port:8090}")
    private String portNumber;

    @RequestMapping(value = "/recommended")
    public String readingList() {
        return "Spring in Action (Manning), Cloud Native Java (O'Reilly), Learning Spring Boot (Packt)";
    }
    @RequestMapping(value = "/greeting")
    public String greeting() {
        System.out.println("Request received on port number " + portNumber);
        return String.format("Hello from '%s with Port Number %s'!", eurekaClient.getApplication(appName)
                .getName(), portNumber);
    }

    public static void main(String[] args) {
        SpringApplication.run(CircuitBreakerBookstoreApplication.class, args);
    }
}
