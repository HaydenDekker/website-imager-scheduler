package com.hdekker.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hdekker.deviceflow.DeviceFlowSubscriberPort;
import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceFlow;

import reactor.core.publisher.Flux;

@RestController
public class DeviceFlowAPI {
	
	@Autowired
	DeviceFlowSubscriberPort port;

	@PostMapping(
			produces = MediaType.TEXT_EVENT_STREAM_VALUE,
			value = Endpoints.DEVICEFLOWS_SUBSCRIBE)
	public Flux<DeviceFlow> subscribe(
			@RequestBody Device device){
		return port.subscribe(device)
					.onErrorMap(err->{
						return new ResponseStatusException(HttpStatus.NOT_FOUND, err.getMessage());
					});
	}
	
}
