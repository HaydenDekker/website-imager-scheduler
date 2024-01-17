package com.hdekker.deviceappflows;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hdekker.device.Device;

@Service
public class ImageScheduler implements AppflowSchedulerPort{
	
	Logger log = LoggerFactory.getLogger(ImageScheduler.class);
	
	Map<Device, FlowSchedule> flowSchedules = new HashMap<>();
	
	@Override
	public Optional<FlowSchedule> schedule(ImageScheduleRequest imageScheduleRequest) {
		
		if(imageScheduleRequest.appFlow()
				.getSiteOrder()
				.size()
				== 0) {
			log.error("Shouldn't be calling this with an empty flow.");
			return Optional.empty();
		}
		
		FlowSchedule fs = new FlowSchedule();
		flowSchedules.put(imageScheduleRequest.device(), fs);
		
		return Optional.of(fs);
	}

	
	
}
