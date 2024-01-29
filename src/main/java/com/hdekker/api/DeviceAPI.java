package com.hdekker.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hdekker.device.DeviceLister;
import com.hdekker.device.DevicePersistance;
import com.hdekker.device.DeviceSupplier;
import com.hdekker.domain.Device;

@RestController()
public class DeviceAPI {
	
	Logger log = LoggerFactory.getLogger(DeviceAPI.class);
	
	@Autowired
	DeviceSupplier deviceSupplier;
	
	@Autowired
	DevicePersistance devicePersistance;
	
	@Autowired
	DeviceLister deviceLister;

	@PostMapping(value = Endpoints.DEVICE_CREATE_NAME)
	public Device createDevice(
			@PathVariable(value = "name") String name) {
		log.info("Device Create " + name);
		return devicePersistance.save(new Device(null, name));
	}
	
	@GetMapping(value = Endpoints.DEVICE_LIST)
	public List<Device> listAllDevices(){
		return deviceLister.allDevices();
	}
	
}
