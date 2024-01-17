package com.hdekker.deviceappflows;

public class DeviceAppflowAssignment {

	Integer id;
	Integer deviceId;
	Integer appFlowId;
	
	public DeviceAppflowAssignment(Integer id, Integer deviceId, Integer appFlowId) {
		super();
		this.id = id;
		this.deviceId = deviceId;
		this.appFlowId = appFlowId;
	}
	public Integer getAppFlowId() {
		return appFlowId;
	}
	public void setAppFlowId(Integer appFlowId) {
		this.appFlowId = appFlowId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	
}
