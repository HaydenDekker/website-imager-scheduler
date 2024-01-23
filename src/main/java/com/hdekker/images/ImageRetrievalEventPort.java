package com.hdekker.images;

import java.util.function.Consumer;

public interface ImageRetrievalEventPort {

	class ImageRetrievalEvent{
		
	}
	
	public Runnable listenForEvents(Consumer<ImageRetrievalEvent> e);
	
}
