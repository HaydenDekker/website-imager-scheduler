package com.hdekker.device;

import com.hdekker.domain.Device;

public interface DeviceSupplier {
	
	public Device get(Integer deviceId);

}
