package com.hdekker.flowschedules;

import java.util.Optional;

import com.hdekker.UseCase;
import com.hdekker.domain.AppFlow;
import com.hdekker.domain.WebsiteDisplayConfiguration;

import reactor.core.publisher.Flux;

@UseCase(name = "Device Flow Scheduling.")
public interface FlowSchedulerPort {
	
	public static enum FlowScheduleEventType{
		TIMER_TRIGGERED
	}
	
	public static class FlowScheduleEvent{
		
		final FlowScheduleEventType eventType;
		final AppFlow flow;
		final WebsiteDisplayConfiguration website;
		
		public FlowScheduleEventType getEventType() {
			return eventType;
		}
		public FlowScheduleEvent(
				FlowScheduleEventType eventType, 
				AppFlow request,
				WebsiteDisplayConfiguration website) {
			super();
			this.eventType = eventType;
			this.flow = request;
			this.website = website;
		}
		public AppFlow getFlow() {
			return flow;
		}
		public WebsiteDisplayConfiguration getWebsite() {
			return website;
		}
		
		
	}
		
	public Optional<FlowTimer> schedule(AppFlow imageScheduleRequest);
}
