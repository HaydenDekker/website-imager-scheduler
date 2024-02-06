package com.hdekker.domain;

import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * The user has to state how and when they want
 * the display to change over time.
 * 
 * @author Hayden Dekker
 *
 */
@Entity
public class AppFlow {

	Integer id;
	String name;
	
	public AppFlow() {
	}
	
	public AppFlow(Integer id, String name, List<WebsiteDisplayConfiguration> siteOrder) {
		super();
		this.name = name;
		this.siteOrder = siteOrder;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	List<WebsiteDisplayConfiguration> siteOrder = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	public List<WebsiteDisplayConfiguration> getSiteOrder() {
		return siteOrder;
	}

	public void setSiteOrder(List<WebsiteDisplayConfiguration> siteOrder) {
		this.siteOrder = siteOrder;
	}

	public Boolean hasWebsite(String websiteName) {
		return this.siteOrder.stream()
					.filter(wdc->wdc.getWebsite().equals(websiteName))
					.findAny()
					.isPresent();
	}

	public boolean hasWebsiteTimer() {
		return this.getSiteOrder()
				.stream()
				.filter(wdc->{
					return wdc.getWebsiteUpdateTime().size()!=0;
				})
				.findAny()
				.isPresent();
	}



}
