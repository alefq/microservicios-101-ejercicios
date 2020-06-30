package com.example.circuitbreakerreading;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CircuitBreakerReadingApplicationTests {

	private MockRestServiceServer server;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private RestTemplate rest;

	@BeforeEach
	public void setup() {
		this.server = MockRestServiceServer.createServer(rest);
	}

	@AfterEach
	public void teardown() {
		this.server = null;
	}

	@Test
	public void toReadTest() {
		this.server.expect(requestTo("http://localhost:8090/recommended"))
				.andExpect(method(HttpMethod.GET)).
				andRespond(withSuccess("books", MediaType.TEXT_PLAIN));
		String books = testRestTemplate.getForObject("/to-read", String.class);
		assertThat(books).isEqualTo("books");
	}

	@Test
	public void toReadFailureTest() {
		this.server.expect(requestTo("http://localhost:8090/recommended")).
				andExpect(method(HttpMethod.GET)).andRespond(withServerError());
		String books = testRestTemplate.getForObject("/to-read", String.class);
		assertThat(books).isEqualTo("Cloud Native Java (O'Reilly)");
	}
}
