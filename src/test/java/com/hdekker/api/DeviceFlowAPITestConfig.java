package com.hdekker.api;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.hdekker.domain.ImageRetrievalEvent;
import com.hdekker.flowschedules.ImageRetrievalEventPort;

import reactor.core.publisher.Flux;
@Configuration
@Profile("device-flow-api")
public class DeviceFlowAPITestConfig {
	
	Logger log = LoggerFactory.getLogger(DeviceFlowAPITestConfig.class);
	
	@Bean
	@Primary
	ImageRetrievalEventPort imEvtPort() {
		return (e) -> {
			Flux.interval(Duration.ofSeconds(1))
			.subscribe(l->e.accept(new ImageRetrievalEvent()));
			return ()->{};
		};
	}

}
