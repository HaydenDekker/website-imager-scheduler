package com.hdekker.database;

import org.springframework.stereotype.Service;

import com.hdekker.device.DevicePersistance;
import com.hdekker.device.DeviceSupplier;
import com.hdekker.domain.Device;

@Service
public class DeviceDatabaseAdapter implements DevicePersistance, DeviceSupplier {

	@Override
	public Device get(Integer deviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Device save(Device device) {
		// TODO Auto-generated method stub
		return null;
	}

}
