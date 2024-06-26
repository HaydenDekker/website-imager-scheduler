package com.hdekker.deviceflow;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.hdekker.TestProfiles;
import com.hdekker.database.AppFlowDatabaseAdapter;
import com.hdekker.database.DeviceDatabaseAdapter;
import com.hdekker.database.DeviceFlowAssignmentDatabaseAdapter;
import com.hdekker.domain.AppFlow;
import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceAppflowAssignment;
import com.hdekker.domain.DeviceFlow;
import com.hdekker.flowschedules.ImageRetrievalEventPortMocks;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * The entry point to the application
 * to subscribe to image updates for flows
 * 
 * @author Hayden Dekker
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles({ 
		TestProfiles.NO_IMAGE_RETRIVAL_PORT,
		TestProfiles.MOCK_DELAYED_500ms_IMAGE_EVENT_PORT})
@DirtiesContext
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
	
	@Test
	public void whenSubscriptionOccurs_ExpectDeviceFlowReturned() {
		
		dev = deviceDatabaseAdapter.save(new Device(1, "DEVICE1"));
		flow = appFlowDatabaseAdapter.save(new AppFlow(1, "appflow1", DeviceFlowSubscriberPortTestConfig.getWebsites()));
		ass = deviceFlowAssignmentDatabaseAdapter.save(new DeviceAppflowAssignment(dev.getId(), flow.getId()));
		log.info("appflow created with id: " + flow.getId());
		
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
		
		dev = deviceDatabaseAdapter.save(new Device(1, "DEVICE1"));
		flow = appFlowDatabaseAdapter.save(new AppFlow(1, "appflow1", DeviceFlowSubscriberPortTestConfig.getWebsites()));
		ass = deviceFlowAssignmentDatabaseAdapter.save(new DeviceAppflowAssignment(dev.getId(), flow.getId()));
		log.info("appflow created with id: " + flow.getId());
		
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
		
		dev = deviceDatabaseAdapter.save(new Device(1, "DEVICE1"));
		flow = appFlowDatabaseAdapter.save(new AppFlow(1, "appflow1", DeviceFlowSubscriberPortTestConfig.getWebsites()));
		ass = deviceFlowAssignmentDatabaseAdapter.save(new DeviceAppflowAssignment(dev.getId(), flow.getId()));
		log.info("appflow created with id: " + flow.getId());
		
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
		
		dev = deviceDatabaseAdapter.save(new Device(1, "DEVICE1"));
		flow = appFlowDatabaseAdapter.save(new AppFlow(1, "appflow1", DeviceFlowSubscriberPortTestConfig.getWebsites()));
		ass = deviceFlowAssignmentDatabaseAdapter.save(new DeviceAppflowAssignment(dev.getId(), flow.getId()));
		log.info("appflow created with id: " + flow.getId());
		
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
