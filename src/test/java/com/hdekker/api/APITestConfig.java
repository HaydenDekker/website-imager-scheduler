package com.hdekker.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
@ConfigurationProperties("test")
public class APITestConfig {
	
	Logger log = LoggerFactory.getLogger(APITestConfig.class);

	public String endpoint;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	@PostConstruct
	public void log() {
		log.info("Endpoint: " + endpoint);
	}
	
}
