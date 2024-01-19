package com.hdekker.image;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hdekker.images.ImageRetrievalPort;

@Component
@Profile("image-port")
@Primary
public class TestImagePort implements ImageRetrievalPort {

	Logger log = LoggerFactory.getLogger(TestImagePort.class);
	
	public List<FlowScheduleEvent> evts = new ArrayList<>();
	
	@Override
	public void onEvent(FlowScheduleEvent evt) {
		log.info("received event.");
		evts.add(evt);
	}

}
