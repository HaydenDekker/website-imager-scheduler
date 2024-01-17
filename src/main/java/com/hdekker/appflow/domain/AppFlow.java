package com.hdekker.appflow.domain;

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

	public class WebsiteDisplayConfiguration{
		
		String website;
		Integer displayDuration;
		
		public WebsiteDisplayConfiguration(String website, Integer displayDuration) {
			super();
			this.website = website;
			this.displayDuration = displayDuration;
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
