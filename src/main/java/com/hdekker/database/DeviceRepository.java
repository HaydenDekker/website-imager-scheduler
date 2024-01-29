package com.hdekker.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hdekker.domain.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer>{
	
}
