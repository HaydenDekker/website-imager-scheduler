package com.hdekker.database;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.deviceflow.DeviceFlowAssignmentSupplier;
import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceAppflowAssignment;
import com.hdekker.flowschedules.DeviceFlowAssignmentDelete;
import com.hdekker.flowschedules.DeviceFlowAssignmentLister;
import com.hdekker.flowschedules.DeviceFlowAssignmentPersistance;

import reactor.core.publisher.Mono;

@Service
public class DeviceFlowAssignmentDatabaseAdapter implements DeviceFlowAssignmentPersistance,
															DeviceFlowAssignmentLister,
															DeviceFlowAssignmentDelete,
															DeviceFlowAssignmentSupplier{

	@Autowired
	DeviceFlowAssignementRepository repository;
	
	@Override
	public DeviceAppflowAssignment save(DeviceAppflowAssignment assignment) {
		return repository.save(assignment); 
	}

	@Override
	public List<DeviceAppflowAssignment> list() {
		return repository.findAll();
	}

	@Override
	public void delete(DeviceAppflowAssignment ass) {
		repository.delete(ass);
	
	}

	@Override
	public Mono<Optional<DeviceAppflowAssignment>> find(Device device) {
		return Mono.just(repository.findByDeviceId(device.getId()));
	}

}
