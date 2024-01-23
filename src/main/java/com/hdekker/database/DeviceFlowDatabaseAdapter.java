package com.hdekker.database;

import com.hdekker.deviceflow.DeviceFlowPersistance;
import com.hdekker.deviceflow.DeviceFlowSupplier;
import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceFlow;

import reactor.core.publisher.Mono;

public class DeviceFlowDatabaseAdapter implements DeviceFlowPersistance, DeviceFlowSupplier{

	@Override
	public Mono<DeviceFlow> getDeviceFlow(Device flow) {
		// TODO Auto-generated method stub
		return null;
	}

}
