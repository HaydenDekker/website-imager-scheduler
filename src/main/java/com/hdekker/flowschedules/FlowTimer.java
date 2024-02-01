package com.hdekker.flowschedules;

import java.time.Duration;
import java.time.OffsetTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hdekker.domain.AppFlow;
import com.hdekker.flowschedules.FlowSchedulerPort.FlowScheduleEvent;
import com.hdekker.flowschedules.FlowSchedulerPort.FlowScheduleEventType;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks.Many;

/**
 * Need to allow websites to be scraped
 * at certain times.
 * 
 * @author Hayden Dekker
 *
 */
public class FlowTimer {
	
	Logger log = LoggerFactory.getLogger(FlowTimer.class);
	
	public FlowTimer(AppFlow appFlow, Many<FlowScheduleEvent> sink) {
		
		appFlow.getSiteOrder()
			.forEach(wdc->{
				wdc.getWebsiteUpdateTime()
					.forEach(os->{
						Mono.delay(
								Duration.between(OffsetTime.now(), os))
						.subscribe(l->{
							log.info("For flow " + appFlow.getName() + " and website " + wdc.getWebsite());
							sink.tryEmitNext(new FlowScheduleEvent(FlowScheduleEventType.TIMER_TRIGGERED, appFlow));
						});
					});
				
			});
		
	}

}
