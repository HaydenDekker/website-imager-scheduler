package com.hdekker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hdekker.appflow.AppFlow;
import com.hdekker.database.AppFlowSupplier;
import com.hdekker.flowschedules.DeviceAppflowAssignment;
import com.hdekker.flowschedules.FlowSchedulerPort;

@Component
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
