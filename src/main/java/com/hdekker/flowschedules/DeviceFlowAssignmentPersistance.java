package com.hdekker.flowschedules;

import com.hdekker.domain.DeviceAppflowAssignment;

public interface DeviceFlowAssignmentPersistance {
	
	DeviceAppflowAssignment save(DeviceAppflowAssignment assignment);

}
