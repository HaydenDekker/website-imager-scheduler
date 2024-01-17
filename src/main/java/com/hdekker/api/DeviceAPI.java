package com.hdekker.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hdekker.device.Device;

@RestController()
public class DeviceAPI {
	
	Logger log = LoggerFactory.getLogger(DeviceAPI.class);

	@PostMapping(value = Endpoints.DEVICE_CREATE_NAME)
	public Device createDevice(
			@PathVariable(value = "name") String name) {
		log.info("Device Create " + name);
		return new Device(0, name);
	}
	
}
