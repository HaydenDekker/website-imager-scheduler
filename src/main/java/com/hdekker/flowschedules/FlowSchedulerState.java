package com.hdekker.flowschedules;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hdekker.flowschedules.FlowSchedulerPort.FlowSchedule;

@Component
public class FlowSchedulerState {

	Map<FlowSchedule, FlowTimer> flowTimers = new HashMap<>();
	
	public Map<FlowSchedule, FlowTimer> getFlowTimers() {
		return flowTimers;
	}
	public void setFlowTimers(Map<FlowSchedule, FlowTimer> flowTimers) {
		this.flowTimers = flowTimers;
	} 
	
	
	
}
