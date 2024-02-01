package com.hdekker.domain;

import java.util.Map;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class DeviceFlow {
	
	Integer id;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * The flow for display on the device
	 */
	AppFlow appFlow;
	
	/**
	 * The associated image for
	 * websites of the flow.
	 * 
	 */
	Map<String, String> fileNameByWebsite;
	
	public DeviceFlow() {
	}

	public DeviceFlow(AppFlow appFlow, Map<String, String> fileNameByWebsite) {
		super();
		this.appFlow = appFlow;
		this.fileNameByWebsite = fileNameByWebsite;
	}

	@OneToOne
	public AppFlow getAppFlow() {
		return appFlow;
	}

	public void setAppFlow(AppFlow appFlow) {
		this.appFlow = appFlow;
	}

	@ElementCollection
	public Map<String, String> getFileNameByWebsite() {
		return fileNameByWebsite;
	}

	public void setFileNameByWebsite(Map<String, String> fileNameByWebsite) {
		this.fileNameByWebsite = fileNameByWebsite;
	}
	
	
}
