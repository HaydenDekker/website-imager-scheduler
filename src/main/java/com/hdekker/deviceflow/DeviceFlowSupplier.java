package com.hdekker.deviceflow;

import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceFlow;

import reactor.core.publisher.Mono;

public interface DeviceFlowSupplier {

	public Mono<DeviceFlow> getDeviceFlow(Device flow);
	
}
