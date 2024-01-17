package com.hdekker.deviceappflows;

import java.util.Optional;

import reactor.core.publisher.Flux;

public interface AppflowSchedulerPort {
	
	public static enum FlowScheduleEventType{
		WEBSITE_IMAGE_UPDATED,
		WEBSITE_IMAGE_FAILED,
		WEBSITE_IMAGER_COMMS_LOSS
	}
	
	public static class FlowScheduleEvent{
		
		final FlowScheduleEventType eventType;
		final ImageScheduleRequest request;
		
		public FlowScheduleEventType getEventType() {
			return eventType;
		}
		public ImageScheduleRequest getRequest() {
			return request;
		}
		public FlowScheduleEvent(FlowScheduleEventType eventType, ImageScheduleRequest request) {
			super();
			this.eventType = eventType;
			this.request = request;
		}
		
	}
	
	public static class FlowSchedule {
		
		ImageScheduleRequest req;
		Flux<FlowScheduleEvent> websiteImages;
		
		public ImageScheduleRequest getReq() {
			return req;
		}
		public void setReq(ImageScheduleRequest req) {
			this.req = req;
		}
		public Flux<FlowScheduleEvent> getWebsiteImages() {
			return websiteImages;
		}
		public void setWebsiteImages(Flux<FlowScheduleEvent> websiteImages) {
			this.websiteImages = websiteImages;
		}
		
	}
	
	public Optional<FlowSchedule> schedule(ImageScheduleRequest imageScheduleRequest);
}
