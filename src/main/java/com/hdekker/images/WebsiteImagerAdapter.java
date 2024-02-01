package com.hdekker.images;

import org.springframework.stereotype.Component;

import com.hdekker.flowschedules.ImageRetrievalPort;
import com.hdekker.domain.ImageRetrievalEvent;
import com.hdekker.flowschedules.FlowSchedulerPort.FlowScheduleEvent;

@Component
public class WebsiteImagerAdapter implements ImageRetrievalPort{

	@Override
	public ImageRetrievalEvent onEvent(FlowScheduleEvent evt) {
		return null;
	}

}
