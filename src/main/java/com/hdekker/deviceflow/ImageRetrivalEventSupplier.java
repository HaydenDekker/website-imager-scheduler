package com.hdekker.deviceflow;

import java.util.List;

import com.hdekker.domain.AppFlow;
import com.hdekker.domain.ImageRetrievalEvent;

public interface ImageRetrivalEventSupplier {
	
	List<ImageRetrievalEvent> allEventsForFlow(AppFlow appFlow);

}
