package com.hdekker.image.server.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
@ConfigurationProperties("image-server")
public class ImageServerConfig {
	
	Logger log = LoggerFactory.getLogger(ImageServerConfig.class);

	String localDirectory;

	public String getLocalDirectory() {
		return localDirectory;
	}

	public void setLocalDirectory(String localDirectory) {
		this.localDirectory = localDirectory;
	}

	@PostConstruct
	public void log() {
		log.info("Image server folder configured at " + localDirectory);
	}
	
}
