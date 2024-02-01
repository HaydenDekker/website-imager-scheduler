package com.hdekker.deviceflow;

import java.util.Optional;

import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceAppflowAssignment;

import reactor.core.publisher.Mono;

public interface DeviceFlowAssignmentSupplier {
	
	Mono<Optional<DeviceAppflowAssignment>> find(Device device);

}
