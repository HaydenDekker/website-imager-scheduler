package com.hdekker.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hdekker.domain.DeviceAppflowAssignment;

@Repository
public interface DeviceFlowAssignementRepository extends JpaRepository<DeviceAppflowAssignment, Integer>{

}
