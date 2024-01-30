package com.hdekker.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hdekker.deviceflow.DeviceFlowSubscriberPort;
import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceFlow;

import reactor.core.publisher.Flux;

@RestController
public class DeviceFlowAPI {
	
	@Autowired
	DeviceFlowSubscriberPort port;

	@GetMapping(value = Endpoints.DEVICEFLOWS_SUBSCRIBE)
	public Flux<DeviceFlow> subscribe(
			@RequestBody Device device){
		return port.subscribe(device);
	}
	
}
