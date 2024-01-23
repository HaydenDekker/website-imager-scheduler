package com.hdekker.flowschedules;

import java.time.Duration;
import java.time.OffsetTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hdekker.domain.AppFlow;
import com.hdekker.domain.AppFlow.WebsiteDisplayConfiguration;
import com.hdekker.flowschedules.FlowScheduleEventListener.FlowScheduleEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.test.StepVerifier;

public class FlowTimerTest {
	
	Logger log = LoggerFactory.getLogger(FlowTimerTest.class);
	
	public static List<WebsiteDisplayConfiguration> websiteDisplayConfigs(OffsetTime triggerTime){
		return List.of(
				new WebsiteDisplayConfiguration("http://hdekker.com", 1, List.of(triggerTime))
			);
	}
	
	public static AppFlow testAppFlow(OffsetTime triggerTime) {
		
		return new AppFlow("TEST_FLOW", websiteDisplayConfigs(triggerTime));

	}

	@Test
	public void whenFlowTimerExpires_expectEventEmitted() {
		
		Many<FlowScheduleEvent> sink = Sinks.many()
				.multicast()
				.directAllOrNothing();
		
		log.info("Running as virtual timer.");
		StepVerifier.withVirtualTime(() -> {
			
			FlowTimer ft = new FlowTimer(testAppFlow(OffsetTime.now().plusSeconds(1)), sink);
			
			Flux<FlowScheduleEvent> events = 
					sink.asFlux();
			return events;
		})
        .expectSubscription()
        .thenAwait(Duration.ofSeconds(10))
        .expectNextMatches(a->{
        	log.info("event " + a.getEventType().name());
        	return true;
        })
        .thenCancel()
        .verify();
		
		
	}
	
}
