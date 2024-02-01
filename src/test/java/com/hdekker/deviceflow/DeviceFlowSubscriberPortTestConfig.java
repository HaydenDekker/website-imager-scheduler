package com.hdekker.deviceflow;

import java.time.Duration;
import java.time.OffsetTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.hdekker.appflow.AppFlowSupplier;
import com.hdekker.domain.AppFlow;
import com.hdekker.domain.DeviceAppflowAssignment;
import com.hdekker.domain.ImageRetrievalEvent;
import com.hdekker.domain.WebsiteDisplayConfiguration;
import com.hdekker.flowschedules.ImageRetrievalEventPort;

import reactor.core.publisher.Mono;

@Configuration
@Profile(value = {"device-flow"})
public class DeviceFlowSubscriberPortTestConfig {
	
	Logger log = LoggerFactory.getLogger(DeviceFlowSubscriberPortTestConfig.class);

	Boolean hasRun = false;
	
	@Bean
	@Primary
	public ImageRetrievalEventPort imageRetrievalEventPort() {
	
		return (e) -> {
			Mono.delay(Duration.ofMillis(500))
				.subscribe(c-> e.accept(new ImageRetrievalEvent()));
			
			return ()->{
				hasRun = true;
				log.info("Deleter Run.");
			};
		};
	}
	
	@Bean
	@Primary
	public DeviceFlowAssignmentSupplier fa() {
		return (d)-> Mono.just(
					Optional.of(new DeviceAppflowAssignment(3, 4)
				));
	}
	
	@Bean
	@Primary
	public AppFlowSupplier afs() {
		return (i) -> new AppFlow(4, "APP_FLOW_1", getWebsites());
	}

	private List<WebsiteDisplayConfiguration> getWebsites() {
		
		return List.of(
				new WebsiteDisplayConfiguration(
						"hdekker.com", 
						20, 
						List.of(OffsetTime.now().plusSeconds(3))));
	}
	
	@Bean
	@Primary
	public ImageRetrivalEventSupplier ires() {
		return (f)-> List.of(new ImageRetrievalEvent(f.getId(), "hdekker_com.png", "hdekker.com"));
	}

}
