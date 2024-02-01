package com.hdekker.flowschedules;

import java.util.function.Consumer;

import com.hdekker.domain.ImageRetrievalEvent;

public interface ImageRetrievalEventPort {
	
	public Runnable listenForEvents(Consumer<ImageRetrievalEvent> e);
	
}
