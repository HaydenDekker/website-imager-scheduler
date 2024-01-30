package com.hdekker.deviceflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.UseCase;
import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceFlow;
import com.hdekker.images.ImageRetrievalEventPort;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

@Service
@UseCase(name = "Subscribe to website image stream")
public class DeviceFlowSubscriberPort {
	
	@Autowired
	DeviceFlowSupplier deviceFlowSupplier;
	
	@Autowired
	ImageRetrievalEventPort imageRetrievalEventPort;
	
	public Flux<DeviceFlow> subscribe(Device device){
		
		Many<DeviceFlow> subscriptionSink = Sinks.many()
			.unicast()
			.onBackpressureBuffer();
		
		deviceFlowSupplier.getDeviceFlow(device)
			.subscribe(c->{
				subscriptionSink.tryEmitNext(c);
			});
		
		imageRetrievalEventPort.listenForEvents(e->{
			deviceFlowSupplier.getDeviceFlow(device)
				.subscribe(c->{
					subscriptionSink.tryEmitNext(c);
				});
		});
		
		return subscriptionSink.asFlux();
	}

}
