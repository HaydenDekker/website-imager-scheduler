package com.hdekker.domain;

import java.time.OffsetTime;
import java.util.List;

import jakarta.persistence.Embeddable;

@Embeddable
public class WebsiteDisplayConfiguration{
	
	String website;
	Integer displayDuration;
	
	// TODO could improve this concept
	// potentially allow windows and frequency.
	List<OffsetTime> websiteUpdateTime;
	
	public WebsiteDisplayConfiguration() {
		// TODO Auto-generated constructor stub
	}
	
	public WebsiteDisplayConfiguration(
			String website, 
			Integer displayDuration,
			List<OffsetTime> websiteUpdateTime) {
		super();
		this.website = website;
		this.displayDuration = displayDuration;
		this.websiteUpdateTime = websiteUpdateTime;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public Integer getDisplayDuration() {
		return displayDuration;
	}
	public void setDisplayDuration(Integer displayDuration) {
		this.displayDuration = displayDuration;
	}
	public List<OffsetTime> getWebsiteUpdateTime() {
		return websiteUpdateTime;
	}
	public void setWebsiteUpdateTime(List<OffsetTime> websiteUpdateTime) {
		this.websiteUpdateTime = websiteUpdateTime;
	}
	
}
