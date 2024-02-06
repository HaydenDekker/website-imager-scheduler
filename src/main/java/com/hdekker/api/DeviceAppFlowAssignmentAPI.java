package com.hdekker.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdekker.domain.DeviceAppflowAssignment;
import com.hdekker.flowschedules.DeviceFlowAssignmentDelete;
import com.hdekker.flowschedules.DeviceFlowAssignmentLister;
import com.hdekker.flowschedules.DeviceFlowAssignmentPersistance;

@RestController
public class DeviceAppFlowAssignmentAPI {
	
	Logger log = LoggerFactory.getLogger(DeviceAppFlowAssignmentAPI.class);

	ObjectMapper om = new ObjectMapper();
	
	@Autowired
	DeviceFlowAssignmentPersistance deviceFlowPersistance;
	
	@Autowired
	DeviceFlowAssignmentLister lister;
	
	@Autowired
	DeviceFlowAssignmentDelete deleter;
	
	@PostMapping(value = Endpoints.DEVICE_APPFLOWS_CREATE)
	public DeviceAppflowAssignment create(
			@RequestBody DeviceAppflowAssignment assignment){
		
		try {
			log.info(om.writeValueAsString(assignment));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		assignment = deviceFlowPersistance.save(assignment);
		
		return assignment;
	}

	@GetMapping(value = Endpoints.DEVICE_APPFLOWS_LIST)
	public List<DeviceAppflowAssignment> list() {
		return lister.list();
	}

	@DeleteMapping(value = Endpoints.DEVICE_APPFLOWS_DELETE)
	public void delete(DeviceAppflowAssignment d) {
		deleter.delete(d);	
	}
	
}
