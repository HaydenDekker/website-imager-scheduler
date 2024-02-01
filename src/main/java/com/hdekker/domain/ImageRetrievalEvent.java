package com.hdekker.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ImageRetrievalEvent {
	
	String id;
	String fileName;
	String websiteName;
	
	public ImageRetrievalEvent() {
	}

	public ImageRetrievalEvent(Integer appFlowId, String fileName,
			String websiteName) {
		this.websiteName = websiteName;
		this.fileName = fileName;
		this.id = "" + appFlowId + "-" + websiteName; 
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}
	
}
