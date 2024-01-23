package com.hdekker.flowschedules;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.domain.AppFlow;
import com.hdekker.flowschedules.FlowScheduleEventListener.FlowScheduleEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

@Service
public class FlowScheduler implements FlowSchedulerPort{
	
	Logger log = LoggerFactory.getLogger(FlowScheduler.class);
	
	@Autowired
	FlowSchedulerState schedulerState;
	
	Many<FlowScheduleEvent> sink = Sinks.many()
			.multicast()
			.directAllOrNothing();

	Flux<FlowScheduleEvent> events = 
			sink.asFlux();
	
	@Autowired
	FlowScheduleEventListener[] listeners;
	
	@Override
	public Optional<FlowSchedule> schedule(AppFlow imageScheduleRequest) {
		
		if(imageScheduleRequest
				.getSiteOrder()
				.size()
				== 0) {
			log.error("Shouldn't be calling this with an empty flow.");
			return Optional.empty();
		}
		
		FlowTimer ft = new FlowTimer(imageScheduleRequest, sink);
		
		FlowSchedule fs = new FlowSchedule(imageScheduleRequest, events, ft);
		schedulerState.getFlowTimers()
			.put(fs, ft);
		
		connectEventListeners();
		
		return Optional.of(fs);
	}
	
	private void connectEventListeners() {
		
		events.subscribe(evt->{
			Arrays.asList(listeners)
			.forEach(c->c.onEvent(evt));
		});
	}

	
	
}
