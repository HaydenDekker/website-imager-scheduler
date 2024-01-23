package com.hdekker.deviceflow;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hdekker.domain.AppFlow;
import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceFlow;

import reactor.core.publisher.Mono;

@Component
@Profile(value = {"device-flow"})
@Primary
public class TestDeviceFlowSupplierPort implements DeviceFlowSupplier {

	@Override
	public Mono<DeviceFlow> getDeviceFlow(Device flow) {
		return Mono.just(new DeviceFlow(new AppFlow("FLOW1", List.of()), Map.of()));
	}

	
	
}
