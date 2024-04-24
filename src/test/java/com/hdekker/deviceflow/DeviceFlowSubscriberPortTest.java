package com.hdekker.deviceflow;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.hdekker.TestProfiles;
import com.hdekker.database.AppFlowDatabaseAdapter;
import com.hdekker.database.DeviceDatabaseAdapter;
import com.hdekker.database.DeviceFlowAssignmentDatabaseAdapter;
import com.hdekker.domain.AppFlow;
import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceAppflowAssignment;
import com.hdekker.domain.DeviceFlow;
import com.hdekker.domain.WebsiteDisplayConfiguration;
import com.hdekker.flowschedules.ImageRetrievalEventPortMocks;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * The entry point to the application
 * to subscribe to image updates for flows
 * 
 * @author Hayden Dekker
 *
 */
@SpringBootTest
@ActiveProfiles({ 
		TestProfiles.NO_IMAGE_RETRIVAL_PORT,
		TestProfiles.MOCK_DELAYED_500ms_IMAGE_EVENT_PORT})
public class DeviceFlowSubscriberPortTest {
	
	Logger log = LoggerFactory.getLogger(DeviceFlowSubscriberPortTest.class);
	
	@Autowired
	public DeviceFlowSubscriberPort deviceFlowSubscriberPort;
	
	@Autowired
	ImageRetrievalEventPortMocks testImageRetrievalEventPort;
	
	@Autowired
	AppFlowDatabaseAdapter appFlowDatabaseAdapter;
	
	@Autowired
	DeviceDatabaseAdapter deviceDatabaseAdapter;
	
	@Autowired
	DeviceFlowAssignmentDatabaseAdapter deviceFlowAssignmentDatabaseAdapter;
	
	Device dev;
	AppFlow flow;
	DeviceAppflowAssignment ass;
	
	public static final Integer TIME_TO_VALIDATE = 10;
	
	@BeforeEach
	public void createTestAppFlow() {
		
		dev = deviceDatabaseAdapter.save(new Device(1, "DEVICE1"));
		flow = appFlowDatabaseAdapter.save(new AppFlow(1, "appflow1", DeviceFlowSubscriberPortTestConfig.getWebsites()));
		ass = deviceFlowAssignmentDatabaseAdapter.save(new DeviceAppflowAssignment(dev.getId(), flow.getId()));
		log.info("appflow created with id: " + flow.getId());
		
	}
	
	@AfterEach
	public void remove() {
		
		deviceDatabaseAdapter.delete(new Device(dev.getId(), "DEVICE1"));
		appFlowDatabaseAdapter.delete(new AppFlow(flow.getId(), "appflow1", List.of(new WebsiteDisplayConfiguration())));
		deviceFlowAssignmentDatabaseAdapter.delete(new DeviceAppflowAssignment(dev.getId(), flow.getId()));
		
	}
	
	@Test
	public void whenSubscriptionOccurs_ExpectDeviceFlowReturned() {
		
		testImageRetrievalEventPort.setImageEvent(ImageRetrievalEventPortMocks.TestEvent);
		
		Device d = new Device(dev.getId(), "DEVICE1");
		
		AppFlow flowDB = appFlowDatabaseAdapter.getFlow(ass.getAppFlowId());
		assertThat(flowDB.getId())
			.isEqualTo(ass.getAppFlowId());
		
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
		.verify(Duration.ofSeconds(TIME_TO_VALIDATE));
		
	}
	
	@Test
	public void whenSubscriptionIsPresent_ExpectDeviceFlowEventsAreBroadcast() {
		
		testImageRetrievalEventPort.setImageEvent(ImageRetrievalEventPortMocks.TestEvent);
		
		Device d = new Device(dev.getId(), "DEVICE1");
		
		StepVerifier.withVirtualTime(()->{
			Flux<DeviceFlow> deviceFlows = deviceFlowSubscriberPort.subscribe(d);
			return deviceFlows;
		})
		.expectSubscription()
		.thenAwait(Duration.ofSeconds(1))
		.expectNextCount(2)
		.thenCancel()
		.verify(Duration.ofSeconds(TIME_TO_VALIDATE));
		
		
	}
	
	@Test
	public void whenSubscriptionIsCancelled_ExpectImageEventPortListenerDeleted() {
		
		testImageRetrievalEventPort.setImageEvent(ImageRetrievalEventPortMocks.TestEvent);
		
		Device d = new Device(dev.getId(), "DEVICE1");
		
		StepVerifier.withVirtualTime(()->{
			Flux<DeviceFlow> deviceFlows = deviceFlowSubscriberPort.subscribe(d);
			return deviceFlows;
		})
		.expectSubscription()
		.thenAwait(Duration.ofSeconds(1))
		.expectNextCount(2)
		.thenCancel()
		.verify(Duration.ofSeconds(TIME_TO_VALIDATE));
		
		assertThat(testImageRetrievalEventPort.deleterHasRun)
			.isEqualTo(true);
		
		
	}
	
	@Test
	public void ignoresIrrelevantEvents() {
		
		testImageRetrievalEventPort.setImageEvent(ImageRetrievalEventPortMocks.IgnoredEvent);
		
		Device d = new Device(dev.getId(), "DEVICE1");
		
		StepVerifier.withVirtualTime(()->{
			Flux<DeviceFlow> deviceFlows = deviceFlowSubscriberPort.subscribe(d);
			return deviceFlows;
		})
		.expectSubscription()
		.thenAwait(Duration.ofSeconds(1))
		.expectNextCount(1)
		.thenCancel()
		.verify(Duration.ofSeconds(TIME_TO_VALIDATE));
		
	}
}
