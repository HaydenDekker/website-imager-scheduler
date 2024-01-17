package com.hdekker.appflow;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.List;

/**
 * The user has to state how and when they want
 * the display to change over time.
 * 
 * @author Hayden Dekker
 *
 */
public class AppFlow {

	Integer id;
	String name;

	public AppFlow(String name, List<WebsiteDisplayConfiguration> siteOrder) {
		super();
		this.name = name;
		this.siteOrder = siteOrder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static class WebsiteDisplayConfiguration{
		
		String website;
		Integer displayDuration;
		
		// TODO could improve this concept
		// potentially allow windows and frequency.
		List<OffsetTime> websiteUpdateTime;
		
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
	
	List<WebsiteDisplayConfiguration> siteOrder;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<WebsiteDisplayConfiguration> getSiteOrder() {
		return siteOrder;
	}

	public void setSiteOrder(List<WebsiteDisplayConfiguration> siteOrder) {
		this.siteOrder = siteOrder;
	}



}
