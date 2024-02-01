package com.hdekker.deviceflow;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceFlow;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * The entry point to the application
 * to subscribe to image updates for flows
 * 
 * @author Hayden Dekker
 *
 */
@SpringBootTest
@ActiveProfiles("device-flow")
public class DeviceFlowSubscriberPortTest {
	
	Logger log = LoggerFactory.getLogger(DeviceFlowSubscriberPortTest.class);
	
	@Autowired
	public DeviceFlowSubscriberPort deviceFlowSubscriberPort;
	
	@Test
	public void whenSubscriptionOccurs_ExpectDeviceFlowReturned() {
		
		Device d = new Device(1, "DEVICE1");
		
		StepVerifier.withVirtualTime(()->{
			Flux<DeviceFlow> deviceFlows = deviceFlowSubscriberPort.subscribe(d);
			return deviceFlows;
		})
		.expectSubscription()
		.thenAwait(Duration.ofSeconds(1))
		.expectNextMatches(df->{
			log.info("" + df.getAppFlow().getName());
			return true;
		})
		.thenCancel()
		.verify(Duration.ofSeconds(1));
		
	}
	
	@Test
	public void whenSubscriptionIsPresent_ExpectDeviceFlowEventsAreBroadcast() {
		
		Device d = new Device(1, "DEVICE1");
		
		StepVerifier.withVirtualTime(()->{
			Flux<DeviceFlow> deviceFlows = deviceFlowSubscriberPort.subscribe(d);
			return deviceFlows;
		})
		.expectSubscription()
		.thenAwait(Duration.ofSeconds(1))
		.expectNextCount(2)
		.thenCancel()
		.verify(Duration.ofSeconds(1));
		
		
	}
	
	@Autowired
	DeviceFlowSubscriberPortTestConfig testImageRetrievalEventPort;
	
	@Test
	public void whenSubscriptionIsCancelled_ExpectImageEventPortListenerDeleted() {
		
		Device d = new Device(1, "DEVICE1");
		
		StepVerifier.withVirtualTime(()->{
			Flux<DeviceFlow> deviceFlows = deviceFlowSubscriberPort.subscribe(d);
			return deviceFlows;
		})
		.expectSubscription()
		.thenAwait(Duration.ofSeconds(1))
		.expectNextCount(2)
		.thenCancel()
		.verify(Duration.ofSeconds(4));
		
		assertThat(testImageRetrievalEventPort.hasRun)
			.isEqualTo(true);
		
		
	}
	
	@Test
	public void ignoresIrrelevantEvents() {
		
	}
}
