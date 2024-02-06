package com.hdekker.deviceflow;

import java.time.OffsetTime;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.hdekker.TestProfiles;
import com.hdekker.appflow.AppFlowSupplier;
import com.hdekker.domain.AppFlow;
import com.hdekker.domain.DeviceAppflowAssignment;
import com.hdekker.domain.WebsiteDisplayConfiguration;
import com.hdekker.flowschedules.ImageRetrievalEventPortMocks;

import reactor.core.publisher.Mono;

@Configuration
public class DeviceFlowSubscriberPortTestConfig {

	
	@Bean
	@Primary
	@Profile(value = {TestProfiles.DUMMY_DEVICE_FLOW_ASSIGNMENT_PROVIDER})
	public DeviceFlowAssignmentSupplier fa() {
		return (d)-> Mono.just(
					Optional.of(new DeviceAppflowAssignment(3, 4)
				));
	}
	
	@Bean
	@Primary
	@Profile(value = {TestProfiles.DUMMY_APPFLOW_SUPPLIER})
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
	@Profile(value = {TestProfiles.MOCK_IMAGE_EVENT_SUPPLIER})
	public ImageRetrivalEventSupplier ires() {
		return (f)-> List.of(ImageRetrievalEventPortMocks.TestEvent);
	}

}
