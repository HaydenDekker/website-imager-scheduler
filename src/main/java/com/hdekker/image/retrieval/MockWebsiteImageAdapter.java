package com.hdekker.image.retrieval;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hdekker.RuntimeProfiles;
import com.hdekker.domain.ImageRetrievalEvent;
import com.hdekker.flowschedules.FlowSchedulerPort.FlowScheduleEvent;
import com.hdekker.flowschedules.ImageRetrievalPort;

import jakarta.annotation.PostConstruct;

/***
 * To allow system integration testing.
 * 
 * Will use a static image to respond to
 * all website events.
 * 
 * @author Hayden Dekker
 *
 */
@Service
@Profile(RuntimeProfiles.MOCK_IMAGE_RETRIEVAL_PORT)
@Primary
public class MockWebsiteImageAdapter implements ImageRetrievalPort {

	Logger log = LoggerFactory.getLogger(MockWebsiteImageAdapter.class);
	
	@Value("${mock-image-if.static-image}")
	String staticImage;
	
	@PostConstruct
	public void log() {
		log.info("port enabled using static image " + staticImage);
	}
	
	@Override
	public ImageRetrievalEvent onEvent(FlowScheduleEvent evt) {
		return new ImageRetrievalEvent(
				staticImage, 
				evt.getWebsite().getWebsite());
	}

}