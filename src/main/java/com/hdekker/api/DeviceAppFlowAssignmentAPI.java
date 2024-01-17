package com.hdekker.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdekker.deviceappflows.DeviceAppflowAssignment;

@RestController
public class DeviceAppFlowAssignmentAPI {
	
	Logger log = LoggerFactory.getLogger(DeviceAppFlowAssignmentAPI.class);

	ObjectMapper om = new ObjectMapper();
	
	@PostMapping(value = Endpoints.DEVICE_APPFLOWS_CREATE)
	public DeviceAppflowAssignment create(
			@RequestBody DeviceAppflowAssignment assignment) throws JsonProcessingException {
		log.info(om.writeValueAsString(assignment));
		return assignment;
	}
	
}
