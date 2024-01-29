package com.hdekker.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.device.DeviceLister;
import com.hdekker.device.DevicePersistance;
import com.hdekker.device.DeviceSupplier;
import com.hdekker.domain.Device;

@Service
public class DeviceDatabaseAdapter implements DevicePersistance, DeviceSupplier, DeviceLister {

	@Autowired
	DeviceRepository deviceRepository;
	
	@Override
	public Device get(Integer deviceId) {
		return deviceRepository.findById(deviceId).get();
	}

	@Override
	public Device save(Device device) {
		return deviceRepository.save(device);
	}

	@Override
	public List<Device> allDevices() {
		return deviceRepository.findAll();
	}

}
