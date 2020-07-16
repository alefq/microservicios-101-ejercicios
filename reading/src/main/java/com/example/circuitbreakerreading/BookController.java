package com.example.circuitbreakerreading;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.configuration.AbstractConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RefreshScope
public class BookController {

    private Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    public static final String MAXIMO_TIEMPO_DE_TOLERANCIA = "5000";

    @Value("${timeoutInMilliseconds:1000}")
    private Long timeout;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }
    
    @GetMapping("/latest")
    @HystrixCommand(fallbackMethod = "fallbackHello", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                    value = MAXIMO_TIEMPO_DE_TOLERANCIA)
    })
    public List<String> latestBooks() throws InterruptedException {
        if (timeout != null) {
            LOGGER.info("timeout: {}", timeout);
            Thread.sleep(timeout);
        } else {
            LOGGER.info("Sin timeout configurado");
            Thread.sleep(3000);
        }
        return Arrays.asList("Dark 4", "Stranger Things 4");
    }

    public List<String> fallbackHello() {
        return Arrays.asList("No hay nuevos libros.");
    }

}
