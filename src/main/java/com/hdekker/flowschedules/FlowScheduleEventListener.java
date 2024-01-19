package com.hdekker.flowschedules;

import java.util.function.Consumer;

import com.hdekker.appflow.AppFlow;

public interface FlowScheduleEventListener {
	
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
	
	public void onEvent(FlowScheduleEvent evt);

}
