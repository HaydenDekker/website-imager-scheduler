package com.hdekker.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.deviceflow.DeviceFlowAssignmentDelete;
import com.hdekker.deviceflow.DeviceFlowAssignmentLister;
import com.hdekker.deviceflow.DeviceFlowAssignmentPersistance;
import com.hdekker.domain.DeviceAppflowAssignment;

@Service
public class DeviceFlowAssignmentDatabaseAdapter implements DeviceFlowAssignmentPersistance,
															DeviceFlowAssignmentLister,
															DeviceFlowAssignmentDelete{

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

}
