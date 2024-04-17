package com.hdekker.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ImageRetrievalEvent {
	
	String fileName;
	String websiteName;
	
	public ImageRetrievalEvent() {
	}

	public ImageRetrievalEvent(String fileName,
			String websiteName) {
		this.websiteName = websiteName;
		this.fileName = fileName;
	}

	@Id
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
