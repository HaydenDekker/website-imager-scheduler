package com.hdekker.api;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

import com.hdekker.RuntimeProfiles;
import com.hdekker.TestProfiles;
import com.hdekker.deviceflow.DeviceFlowSubscriberPortTestConfig;
import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceFlow;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles({ 
	RuntimeProfiles.POSTGRESS,
	TestProfiles.NO_IMAGE_RETRIVAL_PORT,
	TestProfiles.MOCK_IMAGE_EVENT_DB_ADAPTER,
	TestProfiles.MOCK_FLUX_1s_IMAGE_EVENT_PORT,
	TestProfiles.DUMMY_DEVICE_FLOW_ASSIGNMENT_PROVIDER, 
	TestProfiles.DUMMY_APPFLOW_SUPPLIER})
@DirtiesContext()
public class DeviceFlowAPITest {
	
	Logger log = LoggerFactory.getLogger(DeviceFlowAPITest.class);
	
	@Autowired
	WebClient wc;
	
	@Autowired
	DeviceFlowSubscriberPortTestConfig config;
	
	@Test
	public void whenSubscriptionOccurs_ExpectAsyncResponsesGiven(){
		
		Flux<DeviceFlow> flux = 
				wc.post()
			.uri(b->{
				b.path(Endpoints.DEVICEFLOWS_SUBSCRIBE);
				return b.build();
			})
			.bodyValue(new Device(0, "yep"))
			.accept(MediaType.TEXT_EVENT_STREAM)
			.exchangeToFlux(cr->
				cr.bodyToFlux(DeviceFlow.class)
			)
			.doOnNext(df-> log.info("received"));

		StepVerifier.create(flux)
			.expectSubscription()
			.thenAwait(Duration.ofSeconds(4))
			.expectNextCount(5)
			.thenCancel()
			.verify(Duration.ofSeconds(8));
		
	}
	
}
