package com.hdekker.database;

import com.hdekker.device.Device;

public interface DeviceSupplier {
	
	public Device get(Integer deviceId);

}
