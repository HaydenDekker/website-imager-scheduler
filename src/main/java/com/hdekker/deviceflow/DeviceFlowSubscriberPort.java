package com.hdekker.deviceflow;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.UseCase;
import com.hdekker.appflow.AppFlowSupplier;
import com.hdekker.domain.AppFlow;
import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceFlow;
import com.hdekker.domain.ImageRetrievalEvent;
import com.hdekker.flowschedules.ImageRetrievalEventPort;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

@Service
@UseCase(name = "Subscribe to website image stream")
public class DeviceFlowSubscriberPort {
	
	Logger log = LoggerFactory.getLogger(DeviceFlowSubscriberPort.class);
	
	@Autowired
	DeviceFlowAssignmentSupplier deviceFlowAssignmentSupplier;
	
	@Autowired
	AppFlowSupplier appFlowSupplier;

	@Autowired
	ImageRetrivalEventSupplier imageRetrivalEventSupplier;
	
	@Autowired
	ImageRetrievalEventPort imageRetrievalEventPort;
	
	Predicate<ImageRetrievalEvent> eventRelevancePredicate = (e)-> false;
	
	Supplier<DeviceFlow> appFlowSupplierFn = ()-> {
		log.error("This should never be called.");
		return null;
	};
	
	private Supplier<DeviceFlow> registerFlowSupplier(AppFlow appFlow){
		return ()->{
			eventRelevancePredicate = new SubscriptionEventRelevancePredicate(appFlow);
			List<ImageRetrievalEvent> evts =
					imageRetrivalEventSupplier.allEventsForFlow(appFlow);
			DeviceFlow flow = buildFlow(evts, appFlow);
			return flow;
		};
	}

	public Flux<DeviceFlow> subscribe(Device device){
		
		Many<DeviceFlow> subscriptionSink = Sinks.many()
			.unicast()
			.onBackpressureBuffer();
		
		fromDevice(device)
			.subscribe(c-> {
				c.ifPresentOrElse(af->{
					appFlowSupplierFn = registerFlowSupplier(af);
					subscriptionSink.tryEmitNext(appFlowSupplierFn.get());
				}, ()->{
					subscriptionSink.tryEmitComplete();
				});	
			}
		);
		
		
		Runnable deleter = imageRetrievalEventPort.listenForEvents(e->{
			
			if(eventRelevancePredicate.negate().test(e)) return;
			subscriptionSink.tryEmitNext(appFlowSupplierFn.get());
			
		});
		
		return subscriptionSink.asFlux()
					.doFinally(s->deleter.run());
	}
	
	private Mono<Optional<AppFlow>> fromDevice(Device device) {
		return deviceFlowAssignmentSupplier.find(device)
			.map(o->
				o.map(ass->{
					return appFlowSupplier.getFlow(ass.getAppFlowId());
				})
			);
	}
	
	private DeviceFlow buildFlow(List<ImageRetrievalEvent> evts, AppFlow appFlow) {
		return new DeviceFlow(appFlow, 
					evts.stream()
						.collect(Collectors.toMap(ire->ire.getFileName(), ire->ire.getWebsiteName()))
					);
	}

}
