package com.hdekker.deviceflow;

import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceFlow;

import reactor.core.publisher.Flux;

/***
 * A driven port to listen for deviceflow updates.
 * 
 */
public interface DeviceFlowSubscriberPort {
	Flux<DeviceFlow> subscribe(Device device);
}
