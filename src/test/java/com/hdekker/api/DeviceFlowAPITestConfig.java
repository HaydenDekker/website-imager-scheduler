package com.hdekker.api;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.hdekker.deviceflow.DeviceFlowSupplier;
import com.hdekker.domain.DeviceFlow;
import com.hdekker.images.ImageRetrievalEventPort;
import com.hdekker.images.ImageRetrievalEventPort.ImageRetrievalEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
	
	@Bean
	@Primary
	DeviceFlowSupplier deviceFlowSupplier() {
		return (d) -> {
			log.info("Sending mock device flow.");
			return Mono.just(new DeviceFlow(null, null));
		};
	}

}
