package com.hdekker.flowschedules;

import com.hdekker.domain.ImageRetrievalEvent;
import com.hdekker.flowschedules.FlowSchedulerPort.FlowScheduleEvent;

public interface ImageRetrievalPort{
	
	public ImageRetrievalEvent onEvent(FlowScheduleEvent evt);


}
