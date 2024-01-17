package com.hdekker.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class TestWebClient {

	@Autowired
	APITestConfig apiTestConfig;

	@Bean
	public WebClient getWc() {
		return WebClient.builder()
				.baseUrl(apiTestConfig.getEndpoint())
				.build();
	}
	
	
}
