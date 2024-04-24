package com.hdekker.deviceflow;

import java.time.Duration;
import java.time.OffsetTime;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.hdekker.TestProfiles;
import com.hdekker.domain.AppFlow;
import com.hdekker.domain.DeviceFlow;
import com.hdekker.domain.WebsiteDisplayConfiguration;
import reactor.core.publisher.Flux;

@Configuration
@Profile(TestProfiles.DEVICE_FLOW_SUBSCRIBER_API)
public class DeviceFlowSubscriberPortTestConfig {
	
	/***
	 * Only need to confirm the api flux works as expected.
	 * 
	 * @return
	 */
	@Bean
	@Primary
	DeviceFlowSubscriberPort mockPort() {
		return (d)->{
			return Flux.interval(Duration.ofMillis(500))
					.map(l->{
						return new DeviceFlow(
								new AppFlow(0, "test_device_flow", getWebsites()), null);
					});
		};
	}

	public static List<WebsiteDisplayConfiguration> getWebsites() {
		
		return List.of(
				new WebsiteDisplayConfiguration(
						"hdekker.com", 
						20,
						List.of(OffsetTime.now().plusSeconds(3))));
	}


}
