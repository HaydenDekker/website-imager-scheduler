package com.hdekker.image.server.png;

import com.hdekker.domain.ImageRetrievalEvent;

import reactor.core.publisher.Mono;

public interface ImageRetrivalEventPostProcessor {

	public Mono<ImageRetrievalEvent> process(ImageRetrievalEvent evt);
	
}
