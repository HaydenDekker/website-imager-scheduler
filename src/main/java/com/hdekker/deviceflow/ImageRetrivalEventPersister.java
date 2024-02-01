package com.hdekker.deviceflow;

import com.hdekker.domain.ImageRetrievalEvent;

public interface ImageRetrivalEventPersister {
	
	public ImageRetrievalEvent persist(ImageRetrievalEvent evt);

}
