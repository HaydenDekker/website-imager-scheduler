package com.hdekker.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.deviceflow.DeviceFlowAssignmentPersistance;
import com.hdekker.deviceflow.DeviceFlowSupplier;
import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceAppflowAssignment;
import com.hdekker.domain.DeviceFlow;

import reactor.core.publisher.Mono;

@Service
public class DeviceFlowDatabaseAdapter implements DeviceFlowSupplier{

	@Autowired
	DeviceFlowRepository repository;
	
	@Override
	public Mono<DeviceFlow> getDeviceFlow(Device flow) {
		return null;
	}

}
