package com.hdekker.image.retrieval;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hdekker.flowschedules.ImageRetrievalPort;
import com.hdekker.domain.ImageRetrievalEvent;
import com.hdekker.flowschedules.FlowSchedulerPort.FlowScheduleEvent;

import jakarta.annotation.PostConstruct;

@Component
@Profile("website_image_adapter")
public class WebsiteImagerAdapter implements ImageRetrievalPort{
	
	Logger log = LoggerFactory.getLogger(WebsiteImagerAdapter.class);

	@PostConstruct
	public void log() {
		log.info("Port enabled.");
	}
	
	@Override
	public ImageRetrievalEvent onEvent(FlowScheduleEvent evt) {
		// TODO implement
		return null;
	}

}
