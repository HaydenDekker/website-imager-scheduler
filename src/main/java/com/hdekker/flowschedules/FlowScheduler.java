package com.hdekker.flowschedules;

import java.util.Optional;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.appflow.AppFlowLister;
import com.hdekker.domain.AppFlow;
import com.hdekker.domain.ImageRetrievalEvent;

import jakarta.annotation.PostConstruct;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

@Service
public class FlowScheduler implements FlowSchedulerPort, ImageRetrievalEventPort{
	
	Logger log = LoggerFactory.getLogger(FlowScheduler.class);
	
	@Autowired
	AppFlowLister appFlowLister;
	
	@Autowired
	FlowSchedulerState schedulerState;
	
	Many<FlowScheduleEvent> sink = Sinks.many()
			.multicast()
			.directAllOrNothing();

	Flux<FlowScheduleEvent> events = 
			sink.asFlux();
	
	@Autowired
	ImageRetrievalPort imageRetrievalPort;
	
	@PostConstruct
	public void initAppFlows() {
		
		appFlowLister.listAll()
			.forEach(af->{
				schedule(af);
			});
		
	}
	
	@Override
	public Optional<FlowTimer> schedule(AppFlow imageScheduleRequest) {
		
		if(imageScheduleRequest
				.getSiteOrder()
				.size()
				== 0) {
			log.error("Shouldn't be calling this with an empty flow.");
			return Optional.empty();
		}
		
		if(!imageScheduleRequest.hasWebsiteTimer()) {
			log.error("Shouldn't be scheduling without any timers.");
			return Optional.empty();
		}
		
		FlowTimer ft = new FlowTimer(imageScheduleRequest, sink);

		schedulerState.checkForOldSchedule(imageScheduleRequest)
				.ifPresent(ofs->{
					ofs.stop();
					schedulerState.remove(ofs);
					log.info("Stopped existing schedule for " + ofs.flow().getName());
				});
		
		schedulerState.getFlowTimers()
			.put(imageScheduleRequest, ft);
		
		return Optional.of(ft);
	}

	@Override
	public Runnable listenForEvents(Consumer<ImageRetrievalEvent> e) {
		
		Disposable disposable = events.subscribe(evt->{
			ImageRetrievalEvent ire = imageRetrievalPort.onEvent(evt);
			e.accept(ire);
		});
		return ()->{
			disposable.dispose();
			log.warn("Listener disposed.");
		};
		
	}

	
	
}
