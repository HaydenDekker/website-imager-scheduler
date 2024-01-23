package com.hdekker.flowschedules;

import java.util.Optional;

import com.hdekker.domain.AppFlow;
import com.hdekker.flowschedules.FlowScheduleEventListener.FlowScheduleEvent;

import reactor.core.publisher.Flux;

public interface FlowSchedulerPort {
	
	public static record FlowSchedule(
			AppFlow flow,
			Flux<FlowScheduleEvent> websiteImages, 
			FlowTimer flowTimer) {}
		
	public Optional<FlowSchedule> schedule(AppFlow imageScheduleRequest);
}
