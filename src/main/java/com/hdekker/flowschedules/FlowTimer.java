package com.hdekker.flowschedules;

import java.time.Duration;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hdekker.domain.AppFlow;
import com.hdekker.domain.WebsiteDisplayConfiguration;
import com.hdekker.flowschedules.FlowSchedulerPort.FlowScheduleEvent;
import com.hdekker.flowschedules.FlowSchedulerPort.FlowScheduleEventType;

import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks.Many;
import reactor.util.function.Tuples;

/**
 * Need to allow websites to be scraped
 * at certain times.
 * 
 * @author Hayden Dekker
 *
 */
public class FlowTimer {
	
	Logger log = LoggerFactory.getLogger(FlowTimer.class);
	
	Disposable disposable;
	
	final AppFlow flow;
	final Many<FlowScheduleEvent> sink;
	final Map<OffsetTime, List<WebsiteDisplayConfiguration>> sitesByDateTime;
	final List<OffsetTime> timerOrder;
	OffsetTime nextTimer;
	
	public AppFlow flow() {
		return flow;
	}
	
	public FlowTimer(AppFlow appFlow, 
			Many<FlowScheduleEvent> sink) {
		
		flow = appFlow;
		this.sink = sink;
		
		sitesByDateTime = appFlow.getSiteOrder()
			.stream()
			.flatMap(wdc->
				wdc.getWebsiteUpdateTime()
					.stream()
					.map(ot->Tuples.of(ot, wdc))
			)
			.collect(Collectors.toMap(t->t.getT1(), t->List.of(t.getT2()), 
					(a,b)->{ 
						ArrayList<WebsiteDisplayConfiguration> l = new ArrayList<>(a);
						l.addAll(b);
						return l;
					}
			));
		
		timerOrder = sitesByDateTime.keySet()
				.stream()
				.sorted()
				.toList();
		
		OffsetTime n = OffsetTime.now();
		
		nextTimer = timerOrder.stream()
				.filter(t->t.isAfter(n))
				.findFirst()
				.orElse(timerOrder.get(0));
		
		setNextTimer(nextTimer);
		
	}
	
	public void setNextTimer(OffsetTime time) {
		
		Duration toNextTimer = Duration.between(OffsetTime.now(), time);
		if(toNextTimer.isNegative()) {
			toNextTimer = toNextTimer.plusDays(1);
		}
		
		log.info("Setting timer duration " + toNextTimer.toString() + " for timer " + nextTimer.toString());
		
		disposable = Mono.delay(toNextTimer)
			.subscribe(l->{
				
				List<WebsiteDisplayConfiguration> wdcs = sitesByDateTime.get(time);
				
				wdcs.stream()
					.forEach(wdc->{
						
						log.info("For flow " + flow.getName() 
						+ " and website " + wdc.getWebsite());
					
						sink.tryEmitNext(
							new FlowScheduleEvent(
									FlowScheduleEventType.TIMER_TRIGGERED, 
									flow, 
									wdc));
					});
				
				setNextTimer(nextTimer);
				
		});
		
		int pos = timerOrder.indexOf(nextTimer);
		nextTimer = timerOrder.get((pos + 1)% timerOrder.size());
		
	}

	public void stop() {
		disposable.dispose();
	}

}
