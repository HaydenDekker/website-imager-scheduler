package com.hdekker.deviceflow;

import com.hdekker.domain.ImageRetrievalEvent;

import reactor.core.publisher.Mono;

public interface ImageRetrivalEventPersister {
	
	public Mono<ImageRetrievalEvent> persist(ImageRetrievalEvent evt);

}
