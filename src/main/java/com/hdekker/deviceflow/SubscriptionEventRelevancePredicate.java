package com.hdekker.deviceflow;

import java.util.function.Predicate;

import com.hdekker.domain.AppFlow;
import com.hdekker.domain.ImageRetrievalEvent;

/**
 * 
 * @author Hayden Dekker
 *
 */
public class SubscriptionEventRelevancePredicate implements Predicate<ImageRetrievalEvent>{

	Predicate<ImageRetrievalEvent> flowContainsWebsite;
	
	public SubscriptionEventRelevancePredicate(AppFlow appFlow) {
		
		flowContainsWebsite = (ire) -> appFlow.hasWebsite(ire.getWebsiteName());
		
	}
	
	@Override
	public boolean test(ImageRetrievalEvent t) {
		return flowContainsWebsite.test(t);
	}

}
