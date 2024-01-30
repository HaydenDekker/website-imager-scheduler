package com.hdekker.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DeviceAppflowAssignment {

	Integer id;
	Integer deviceId;
	Integer appFlowId;
	
	public DeviceAppflowAssignment() {
	}
	
	public DeviceAppflowAssignment(Integer deviceId, Integer appFlowId) {
		super();
		this.deviceId = deviceId;
		this.appFlowId = appFlowId;
	}

	public Integer getAppFlowId() {
		return appFlowId;
	}
	public void setAppFlowId(Integer appFlowId) {
		this.appFlowId = appFlowId;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
