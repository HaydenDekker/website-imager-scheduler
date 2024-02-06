package com.hdekker.flowschedules;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hdekker.domain.AppFlow;

import jakarta.annotation.PreDestroy;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Component
public class FlowSchedulerState {
	
	Logger log = LoggerFactory.getLogger(FlowSchedulerState.class);
	
	Disposable disposable;
	
	public FlowSchedulerState() {
		disposable = Flux.interval(Duration.ofSeconds(10))
		.subscribe(c->{
			log.info("" + flowTimers.size() + " flow timers registered.");
		});
	}

	Map<AppFlow, FlowTimer> flowTimers = new HashMap<>();
	
	public Map<AppFlow, FlowTimer> getFlowTimers() {
		return flowTimers;
	}
	public void setFlowTimers(Map<AppFlow, FlowTimer> flowTimers) {
		this.flowTimers = flowTimers;
	} 
	
	@PreDestroy
	public void closeLogger() {
		disposable.dispose();
	}
	
	public Optional<FlowTimer> checkForOldSchedule(AppFlow imageScheduleRequest) {
		
		return flowTimers
			.entrySet()
			.stream()
			.filter(es->es.getKey().getId().equals(imageScheduleRequest.getId()))
			.findFirst()
			.map(es->es.getValue());

	}
	public void remove(FlowTimer fs) {
		flowTimers.remove(fs.flow());
	}
	
	
}
