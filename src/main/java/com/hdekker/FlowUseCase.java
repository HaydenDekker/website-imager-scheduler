package com.hdekker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.hdekker.appflow.AppFlowSupplier;
import com.hdekker.domain.AppFlow;
import com.hdekker.domain.DeviceAppflowAssignment;
import com.hdekker.flowschedules.FlowSchedulerPort;

@Service
@UseCase(name = "Device Flow Scheduling.")
public class FlowUseCase {
	
	@Autowired
	FlowSchedulerPort flowSchedulerPort;
	
	@Autowired
	AppFlowSupplier appFlowSupplier;
	
	public void scheduleFlow(DeviceAppflowAssignment assignment) {
		
		AppFlow flow = appFlowSupplier.getFlow(assignment.getAppFlowId());
		flowSchedulerPort.schedule(flow);
		
	}
	
	

}
