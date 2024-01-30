package com.hdekker.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hdekker.domain.DeviceAppflowAssignment;
import com.hdekker.domain.DeviceFlow;

public interface DeviceFlowRepository extends JpaRepository<DeviceFlow, Integer> {

}
