package com.hdekker.flowschedules;

import java.util.Optional;

import com.hdekker.domain.AppFlow;

import reactor.core.publisher.Flux;

public interface FlowSchedulerPort {
	
	public static enum FlowScheduleEventType{
		TIMER_TRIGGERED
	}
	
	public static class FlowScheduleEvent{
		
		final FlowScheduleEventType eventType;
		final AppFlow flow;
		
		public FlowScheduleEventType getEventType() {
			return eventType;
		}
		public AppFlow getRequest() {
			return flow;
		}
		public FlowScheduleEvent(FlowScheduleEventType eventType, AppFlow request) {
			super();
			this.eventType = eventType;
			this.flow = request;
		}
		
	}
	
	public static record FlowSchedule(
			AppFlow flow,
			Flux<FlowScheduleEvent> websiteImages, 
			FlowTimer flowTimer) {}
	
		
	public Optional<FlowSchedule> schedule(AppFlow imageScheduleRequest);
}
