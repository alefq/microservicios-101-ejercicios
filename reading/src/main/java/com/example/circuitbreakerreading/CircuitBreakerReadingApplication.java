package com.example.circuitbreakerreading;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.discovery.EurekaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@RestController
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class CircuitBreakerReadingApplication {

    private Logger LOGGER = LoggerFactory.getLogger(CircuitBreakerReadingApplication.class);


    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Autowired
    private ApplicationInfoManager applicationInfoManager;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String portNumber;

    @Autowired
    private BookService bookService;

    @Bean
    public RestTemplate rest(RestTemplateBuilder builder) {
        return builder.build();
    }

    @RequestMapping("/to-read")
    public String toRead() {
        return bookService.readingList();
    }

    @RequestMapping(value = "/greeting")
    public String greeting() {
        LOGGER.info("Request received on port number " + portNumber);
        return String.format("Server: %s - Hello from '%s with Port Number %s'!",
                applicationInfoManager.getEurekaInstanceConfig(),
                eurekaClient.getApplication(appName).getName(), portNumber);
    }

    public static void main(String[] args) {
        SpringApplication.run(CircuitBreakerReadingApplication.class, args);
    }

}
