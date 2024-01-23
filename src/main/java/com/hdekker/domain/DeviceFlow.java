package com.hdekker.domain;

import java.util.Map;

public class DeviceFlow {

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

	public DeviceFlow(AppFlow appFlow, Map<String, String> fileNameByWebsite) {
		super();
		this.appFlow = appFlow;
		this.fileNameByWebsite = fileNameByWebsite;
	}

	public AppFlow getAppFlow() {
		return appFlow;
	}

	public void setAppFlow(AppFlow appFlow) {
		this.appFlow = appFlow;
	}

	public Map<String, String> getFileNameByWebsite() {
		return fileNameByWebsite;
	}

	public void setFileNameByWebsite(Map<String, String> fileNameByWebsite) {
		this.fileNameByWebsite = fileNameByWebsite;
	}
	
	
}
