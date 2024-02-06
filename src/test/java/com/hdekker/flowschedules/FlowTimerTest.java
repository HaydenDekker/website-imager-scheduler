package com.hdekker.flowschedules;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.OffsetTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hdekker.domain.AppFlow;
import com.hdekker.domain.WebsiteDisplayConfiguration;
import com.hdekker.flowschedules.FlowSchedulerPort.FlowScheduleEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.test.StepVerifier;

public class FlowTimerTest {
	
	Logger log = LoggerFactory.getLogger(FlowTimerTest.class);
	
	public static List<WebsiteDisplayConfiguration> websiteDisplayConfigs(List<OffsetTime> triggerTime){
		return List.of(
				new WebsiteDisplayConfiguration("http://hdekker.com", 1, triggerTime),
						new WebsiteDisplayConfiguration("http://bodug.com", 30, triggerTime)
			);
	}
	
	public static AppFlow testAppFlow(List<OffsetTime> triggerTime) {
		
		return new AppFlow(null, "TEST_FLOW", websiteDisplayConfigs(triggerTime));

	}

	@Test
	public void whenFlowTimerExpires_expectEventEmitted() {
		
		Many<FlowScheduleEvent> sink = Sinks.many()
				.multicast()
				.directAllOrNothing();
		
		log.info("Running as virtual timer.");
		StepVerifier.withVirtualTime(() -> {
			
			FlowTimer ft = new FlowTimer(testAppFlow(List.of(OffsetTime.now().plusSeconds(1))), sink);
			
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
	
	@Test
	public void whenTwoOrMoreTimesExistForAppFlow_ExpectOnlyTheNextTimerIsPresent() {
		
		Many<FlowScheduleEvent> sink = Sinks.many()
				.multicast()
				.directAllOrNothing();
		
		FlowTimer ft = new FlowTimer(
				testAppFlow(
					List.of(OffsetTime.now().plusSeconds(1),
							OffsetTime.now().plusSeconds(2))), sink);
		
		assertThat(ft.disposable)
			.isNotNull();
		
	}
	
	@Test
	public void whenTimerExpires_ExpectNextTimerIsStarted() {
		
		Many<FlowScheduleEvent> sink = Sinks.many()
				.multicast()
				.directAllOrNothing();
		
		FlowTimer ft = new FlowTimer(testAppFlow(List.of(OffsetTime.now().plusSeconds(1), OffsetTime.now().plusSeconds(2))), sink);
		
		log.info("ft " + ft.timerOrder.size());
		log.info("sitesByDateTime " + ft.sitesByDateTime.size());
		
		Flux<FlowScheduleEvent> events = 
				sink.asFlux()
				.doOnNext(e->log.info("" + ft.nextTimer.toString()));
		StepVerifier.create(events)
	        .expectSubscription()
	        .thenAwait(Duration.ofSeconds(10))
	        .expectNextCount(4)
	        .thenCancel()
	        .verify();
		
	}

	
}
