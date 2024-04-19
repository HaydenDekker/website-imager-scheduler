package com.hdekker.database;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hdekker.TestProfiles;
import com.hdekker.device.DeviceDeleter;
import com.hdekker.device.DeviceLister;
import com.hdekker.device.DevicePersistance;
import com.hdekker.device.DeviceSupplier;
import com.hdekker.domain.Device;

@Component
@Profile(TestProfiles.NO_DB_CONFIGURATION)
@Primary
public class DummyDeviceDatabaseAdapter implements DevicePersistance, DeviceSupplier, DeviceLister, DeviceDeleter {

	@Override
	public void delete(Device device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Device> allDevices() {
		// TODO Auto-generated method stub
		return null;
	}

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
