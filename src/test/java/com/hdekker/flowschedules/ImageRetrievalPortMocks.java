package com.hdekker.flowschedules;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.hdekker.TestProfiles;
import com.hdekker.domain.ImageRetrievalEvent;
import com.hdekker.flowschedules.FlowSchedulerPort.FlowScheduleEvent;

@Configuration
public class ImageRetrievalPortMocks {
	
	Logger log = LoggerFactory.getLogger(ImageRetrievalPortMocks.class);
	
	@Bean
	@Primary
	@Profile(TestProfiles.NO_IMAGE_RETRIVAL_PORT)
	public ImageRetrievalPort noImageRetrievalPort() {
		return (evt)->{
			log.error("Shouldn't have been called for test.");
			return ImageRetrievalEventPortMocks.TestEvent;
		};
	}
	
	public List<FlowScheduleEvent> evts = new ArrayList<>();
	
	@Bean
	@Primary
	@Profile(TestProfiles.MOCK_IMAGE_RETRIEVAL_PORT)
	public ImageRetrievalPort mockImageRetrievalPort() {
		return (evt) -> {
				log.info("received event.");
				evts.add(evt);
				return new ImageRetrievalEvent("TEST_FILENAME", evt.getWebsite().getWebsite());
		};
	}

}
