package com.hdekker.database;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hdekker.TestProfiles;
import com.hdekker.deviceflow.DeviceFlowAssignmentSupplier;
import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceAppflowAssignment;
import com.hdekker.flowschedules.DeviceFlowAssignmentDelete;
import com.hdekker.flowschedules.DeviceFlowAssignmentLister;
import com.hdekker.flowschedules.DeviceFlowAssignmentPersistance;

import reactor.core.publisher.Mono;

@Component
@Profile(TestProfiles.NO_DB_CONFIGURATION)
@Primary
public class DummyDeviceFlowAssignmentDatabaseAdapter implements DeviceFlowAssignmentPersistance,
DeviceFlowAssignmentLister,
DeviceFlowAssignmentDelete,
DeviceFlowAssignmentSupplier{

	@Override
	public Mono<Optional<DeviceAppflowAssignment>> find(Device device) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(DeviceAppflowAssignment ass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DeviceAppflowAssignment> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeviceAppflowAssignment save(DeviceAppflowAssignment assignment) {
		// TODO Auto-generated method stub
		return null;
	}

}
